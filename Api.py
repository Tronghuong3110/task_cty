from fastapi import FastAPI
from option import Option
from signup import SignUp
import uvicorn

app = FastAPI()

@app.get('/api/')
def main(profilePath: str, listPhoneNumber: str):
    phoneNumbers = []
    for phoneNumber in listPhoneNumber.split(', '):
        phoneNumber = phoneNumber.replace('[', '')
        phoneNumber = phoneNumber.replace(']', '')
        phoneNumbers.append(phoneNumber)

    options = Option(profilePath)
    option = options.createOption()

    signupFace = SignUp(phoneNumbers, option)
    signupFace.run()
    # signupFace.printPhoneNumber()
    return 'success'

if __name__ == '__main__':
    uvicorn.run(app, host='127.0.0.1', port=8000)  # Định nghĩa cổng 8000

