import time
import requests
from datetime import datetime
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.common.exceptions import NoSuchFrameException
from selenium.common.exceptions import NoSuchElementException

class SignUp:
    def __init__(self) -> None:
        pass

    def setValue(self, phoneNumbers, option, action):
        self.phoneNumbers = phoneNumbers
        self.option = option
        self.action = action
        self.finish = False

    def stop(self):
        while(self.action != 1):
            time.sleep(5)

    def close_browser(self, driver):
        driver.delete_all_cookies()
        driver.quit()

    # sign up face
    def signupface(self, driver, phoneNumber, index):
        driver.get("https://www.facebook.com")
        try:
            # click dang ki
            self.stop()
            if(self.finish == True):
                self.close_browser(driver)
                return "Đã kết thúc"
            btnSignUp = driver.find_element(By.XPATH, '/html/body/div[1]/div[1]/div[1]/div/div/div/div[2]/div/div[1]/form/div[5]/a')
            btnSignUp.click() 
        except NoSuchElementException:
            print("Can not found button create account")
        time.sleep(10)
        # fillout form
        fillOutForm(driver, phoneNumber)
        # click submit form sign up
        print("Sign up account")
        self.stop()
        if(self.finish == True):
            self.close_browser(driver)
            return 'finish'
        print("finish ", self.finish)
        driver.find_element(By.NAME, 'websubmit').click()
        time.sleep(3)
        try:
            message = driver.find_element(By.CLASS_NAME, '_58mo').text
            print("Message error ", message)
            if(message != ''):
                self.close_browser(driver)
                self.sendProcess(phoneNumber, "Số điện thoại đã tồn tại", index, "Thất bại")
                return None
        except NoSuchElementException :
            print("OK")
        return ""


    # Send progress resolving
    def sendProcess(self, current_phoneNumber, message, index, status):
        urlJava = "http://localhost:8081/api/process"
        data = {
            'current_phoneNumber':  current_phoneNumber,
            'message': message,
            'index': index,
            'status': status
        }
        print("current_phoneNumber: ", data['current_phoneNumber'])
        print("status: ", data['status'])
        response = requests.post(urlJava, json=data)
        print("Response api java ", response)

    def sendTimeSend(self):
        urlJava = "http://localhost:8081/api/time_send"
        time = datetime.now()
        print(time.strftime("%d-%m-%Y %H:%M:%S"))
        requests.post(urlJava, params={"time": time.strftime("%d-%m-%Y %H:%M:%S")})

    def run(self):
        for i, phoneNumber in enumerate(self.phoneNumbers, start=1):
            if(self.finish == True):
                return "Đã kết thúc"
            check_resolve_captcha = True
            check_send_Sms = False
            message = ""
            self.stop()
            self.sendProcess(phoneNumber, "Đang thực hiện", i, "Đang thực hiện")
            if(self.finish == True):
                self.close_browser(driver)
                return "Đã kết thúc"
            print("Working on phone number ", phoneNumber)
            driver = None
            # solve byPass captcha
            while True and self.action == 1 and self.finish == False:
                driver = webdriver.Chrome(options=self.option) 
                try:
                    message = self.signupface(driver, phoneNumber, i)
                    if(message == None or message == "finish"):
                        break
                    # click button continue if exists
                    time.sleep(20)
                    try:
                        self.stop()
                        if(self.finish == True):
                            self.close_browser(driver)
                            return "Đã kết thúc"
                        btnContinue = driver.find_element(By.XPATH, '/html/body/div/div/div/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div/div[1]/div/div/div/div/div/div/div/div[2]/div/div/div[2]/div/div/div')
                        btnContinue.click()
                    except NoSuchElementException:
                        print("Can't not found button continue")

                    # click button to Bypass captcha use api of 2 captcha
                    time.sleep(20)
                    try:
                        driver.switch_to.frame('captcha-recaptcha')
                        time.sleep(20)
                        self.stop()
                        if(self.finish == True):
                            self.close_browser(driver)
                            return "Đã kết thúc"
                        resolveCaptcha = driver.find_element(By.XPATH, '/html/body/div[1]/div[2]/div[2]')
                        resolveCaptcha.click()
                        time.sleep(70)
                        driver.switch_to.default_content()
                        try:
                            # click continue when Bypass captcha success
                            time.sleep(30)
                            self.stop()
                            if(self.finish == True):
                                self.close_browser(driver)
                                return "Đã kết thúc"
                            conti = driver.find_element(By.XPATH, '/html/body/div/div/div/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div/div[1]/div/div/div/div/div/div/div/div[3]/div/div/div')
                            conti.click()
                            self.sendTimeSend() # Case bypass captcha success and can request facebook send SMS
                            check_send_Sms = True
                            break
                        except NoSuchElementException: 
                            print('Can not ByPass captcha')
                            if(self.finish == True):
                                self.close_browser(driver)
                                return "Đã kết thúc"
                            self.sendProcess(phoneNumber, "Thất bại", i, "Lỗi giải captcha sai!")
                            check_resolve_captcha = False
                            break

                    except NoSuchElementException:
                        print("No need to by pass captcha")
                    except NoSuchFrameException:
                        print("Error switch to frame!")
                        break

                # Can not found any element in website
                except NoSuchElementException : 
                    print('Error can not found element!')   
                    self.close_browser(driver)
            self.stop()
            if(self.finish == True):
                self.close_browser(driver)
                return "Đã kết thúc"
            # End While  
            if(check_resolve_captcha == False):
                self.close_browser(driver)
                continue
            # case phone number exsist
            if(message == None):
                time.sleep(10)
                continue
            elif(message == 'Đã kết thúc'):
                return 'Đã kết thúc'
            # solve send SMS again
            time.sleep(10)
            try:
                self.stop()
                if(self.finish == True):
                    self.close_browser(driver)
                    return "Đã kết thúc"
                sendSMSAgain = driver.find_element(By.XPATH, '/html/body/div[1]/div[2]/div[1]/div/div/div[1]/div[2]/form/div[1]/div[3]/a')
                sendSMSAgain.click()
            except NoSuchElementException:
                print("No need to Send SMS Again!")

            # Resolve to re-enter the phone number and require send SMS
            time.sleep(10)
            try:
                self.stop()
                if(self.finish == True):
                    self.close_browser(driver)
                    return "Đã kết thúc"
                driver.find_element(By.XPATH, '/html/body/div/div/div/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div/div[1]/div/div/div/div/div/div/div/div[2]/div/label/div/div[2]/input').send_keys(phoneNumber)
                sendSMS = driver.find_element(By.XPATH, '/html/body/div/div/div/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div/div[1]/div/div/div/div/div/div/div/div[4]/div/div/div')
                print("Button Send SMS ", sendSMS)
                sendSMS.click()
                if(not check_send_Sms): # Case of checkpoint ==> send again phone number
                    self.sendTimeSend()
                # time.sleep(50)
            except NoSuchElementException:
                print("Check point error")

            # wait SMS send to phoneNumber
            self.sendTimeSend()
            self.sendProcess(phoneNumber, "Đang chờ SMS", i, "")
            self.stop()
            time.sleep(120) # chờ 2 phút
            self.close_browser(driver)
        return "Đã hoàn thành"
    
# fill out infor user
def fillOutForm(driver, phoneNumber):   
    driver.find_element(By.NAME, 'lastname').send_keys('Jhon')# Ho
    driver.find_element(By.NAME, 'firstname').send_keys('Tech') # ten
    driver.find_element(By.NAME, 'reg_email__').send_keys(phoneNumber) # so dien thoai
    driver.find_element(By.NAME, 'reg_passwd__').send_keys('123456789@') # mat khau
    driver.find_element(By.NAME, 'birthday_day').send_keys('31') # ngay sinh
    driver.find_element_by_id('month').send_keys('Jan') # thang sinh
    driver.find_element(By.NAME, 'birthday_year').send_keys('2000') # nam sinh
    driver.find_element(By.NAME, 'sex').click() # gioi tinh (nam)



# if __name__ == '__main__':
#     listPhoneNumber = ['0769045906', '0782357678', '0782353979', '0769119699', '0769118787', '0769108833',
#     '0769098833' ,'0769097567' , '0769078877', '0769078822', '0769078811']
#     profilePath = "C:/Users/ASUS/.hidemyacc/profiles/profile 2"
#     options = Option(profilePath)
#     option = options.createOption()
#     signUp = SignUp(listPhoneNumber, option)
#     signUp.run()

# try: 
#     # click reSend SMS
#     time.sleep(10)
#     sendSMSAgain1 = driver.find_element(By.XPATH, '/html/body/div/div/div/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div/div[1]/div/div/div/div/div/div/div/div[3]/div/div[1]/span')
#     sendSMSAgain2 = driver.find_element(By.XPATH, '/html/body/div/div/div/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div/div[1]/div/div/div/div/div/div/div/div[3]/div/div[1]/span/div')
#     # sendSMSAgain.click()
#     print('sendSMSAgain1', sendSMSAgain1)
#     print('sendSMSAgain2', sendSMSAgain2)
# except NoSuchElementException:
#     print("Check point error, Can not resend SMS")