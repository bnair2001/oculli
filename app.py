#Imports
import os
import sys
import math
from gtts import gTTS 
module_path = os.path.abspath(os.path.join('..'))
if module_path not in sys.path:
    sys.path.append(module_path)
import torch
import torch.nn as nn
import torch.backends.cudnn as cudnn
from torch.autograd import Variable
import numpy as np
import cv2
if torch.cuda.is_available():
    torch.set_default_tensor_type('torch.cuda.FloatTensor')
from ssd import build_ssd
from flask import Flask, escape, request, jsonify, send_file

#Loading the pre-trained SSD model
net = build_ssd('test', 300, 21)    # initialize SSD
net.load_weights('./weights/ssd300_mAP_77.43_v2.pth')


app = Flask(__name__)

#Post request to run inference on image
@app.route('/api',  methods=['POST'])
def api():
    
    if 'image' not in request.files:
        msg= {"message":"Please send the file as well", "code":1}
        return jsonify(msg)

    img = request.files['image']
    img.save("tmp/test.png")
    image = cv2.imread("./tmp/test.png")
    #Resizing the image
    x = cv2.resize(image, (300, 300)).astype(np.float32)
    x -= (104.0, 117.0, 123.0)
    x = x.astype(np.float32)
    x = x[:, :, ::-1].copy()
    x = torch.from_numpy(x).permute(2, 0, 1)

    
    xx = Variable(x.unsqueeze(0))   
    # Running inference
    if torch.cuda.is_available():
        xx = xx.cuda()
    y = net(xx)
    
    #Loading the results into a json file
    detections = y.data
    from data import VOC_CLASSES as labels
    possib=[]
    frequency =[]
    for i in range(detections.size(1)):
        j = 0
        while detections[0,i,j,0] >= 0.6:
            score = detections[0,i,j,0]
            label_name = labels[i-1]
            if score.item() > 0.60:
                if label_name in possib:
                    frequency[possib.index(label_name)] = frequency[possib.index(label_name)] + 1
                else:
                    possib.append(label_name)
                    frequency.append(1)
            j+=1
    print(possib)
    print(frequency)
    mess = "There are "
    for i in range(len(possib)):
        mess = mess + str(frequency[i])+" "+possib[i]+", "
    mess = mess + "in that direction"
    language = 'en'
    myobj = gTTS(text=mess, lang=language, slow=False)
    path_to_file = 'tmp/TTS.mp3'
    myobj.save(path_to_file)
    os.remove("./tmp/test.png")
    data ={ "message": mess, "code":0}
    return send_file(path_to_file, mimetype="audio/mp3", as_attachment = True, attachment_filename="TTS.mp3")

@app.route('/')
def home():
    return "Hello World"
