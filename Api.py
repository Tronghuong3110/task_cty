from fastapi import FastAPI
from option import Option
from signup import SignUp
import uvicorn

app = FastAPI()
signupFace = SignUp()

@app.get('/api/start')
def main(profilePath: str, listPhoneNumber: str):
    phoneNumbers = []
    for phoneNumber in listPhoneNumber.split(', '):
        phoneNumber = phoneNumber.replace('[', '')
        phoneNumber = phoneNumber.replace(']', '')
        phoneNumbers.append(phoneNumber)

    options = Option(profilePath)
    option = options.createOption()
    signupFace.setValue(phoneNumbers, option, 1)
    message = signupFace.run()
    print ("message ", message)
    # signupFace.printPhoneNumber()
    return message

@app.get("/api/action/")
def pause(action:str):
    if(action == "pause"):
        signupFace.action = 0
        print(action)
    elif(action == "continue"):
        signupFace.action = 1
        print(action)
    else:
        signupFace.finish = True

if __name__ == '__main__':
    uvicorn.run(app, host='127.0.0.1', port=8888)  # Định nghĩa cổng 8000

