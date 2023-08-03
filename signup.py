import time
import requests
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.common.exceptions import NoSuchFrameException
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.support import expected_conditions as EC

class SignUp:
    def __init__(self, phoneNumbers, option):
        self.phoneNumbers = phoneNumbers
        self.option = option

    # sign up face
    def signupface(self, driver, phoneNumber):
        driver.get("https://www.facebook.com")
        try:
            # click dang ki
            # btnSignUp = WebDriverWait(driver, timeout=10).until(lambda d: )
            btnSignUp = driver.find_element(By.XPATH, '/html/body/div[1]/div[1]/div[1]/div/div/div/div[2]/div/div[1]/form/div[5]/a')
            btnSignUp.click() 
        except NoSuchElementException:
            print("Can't found button create account")
        time.sleep(10)
        # fillout form
        fillOutForm(driver, phoneNumber)
        # click submit form sign up
        print("Sign up account")
        driver.find_element(By.NAME, 'websubmit').click()

    # Send progress resolving
    def sendProgress(self, current_phoneNumber, message):
        urlJava = "http://localhost:8081/api/progress"
        data = {
            'current_phoneNumber':  current_phoneNumber,
            'message': message
        }
        response = requests.post(urlJava, json=data)

    def run(self):
        for phoneNumber in self.phoneNumbers:
            self.sendProgress(phoneNumber, "Đang thực hiện")
            time.sleep(10)
            print("Working on phone number ", phoneNumber)
            conti = None
            driver = None
            # solve byPass captcha
            while True:
                driver = webdriver.Chrome(options=self.option) 
                try:
                    self.signupface(driver, phoneNumber)
                    # click button continue if exists
                    time.sleep(20)
                    try:
                        # btnContinue = WebDriverWait(driver, timeout=20).until(lambda d: d.find_element(By.XPATH, '/html/body/div/div/div/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div/div[1]/div/div/div/div/div/div/div/div[2]/div/div/div[2]/div/div/div'))
                        btnContinue = driver.find_element(By.XPATH, '/html/body/div/div/div/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div/div[1]/div/div/div/div/div/div/div/div[2]/div/div/div[2]/div/div/div')
                        btnContinue.click()
                    except NoSuchElementException:
                        print("Can't not found button continue")

                    # click button to Bypass captcha use api of 2 captcha
                    time.sleep(20)
                    try:
                        driver.switch_to.frame('captcha-recaptcha')
                        # WebDriverWait(driver, timeout=20).until(lambda d: d.switch_to.frame('captcha-recaptcha'))
                        time.sleep(20)
                        resolveCaptcha = driver.find_element(By.XPATH, '/html/body/div[1]/div[2]/div[2]')
                        # resolveCaptcha = WebDriverWait(driver, timeout=20).until(lambda d: d.find_element(By.XPATH, '/html/body/div[1]/div[2]/div[2]'))
                        resolveCaptcha.click()
                        time.sleep(70)
                        driver.switch_to.default_content()
                        # WebDriverWait(driver, timeout=70).until(lambda d: d.switch_to.default_content())
                        
                        try:
                            # click continue when Bypass captcha success
                            time.sleep(30)
                            # conti = WebDriverWait(driver, timeout=70).until(lambda d: d)
                            conti = driver.find_element(By.XPATH, '/html/body/div/div/div/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div/div[1]/div/div/div/div/div/div/div/div[3]/div/div/div')
                            conti.click()
                            break
                        except NoSuchElementException: 
                            print('Can not ByPass captcha')
                            self.sendProgress(phoneNumber, "Lỗi giải captcha sai!")
                            break

                    except NoSuchElementException:
                        print("No need to by pass captcha")
                    except NoSuchFrameException:
                        print("Error switch to frame!")
                        break

                # Can not found any element in website
                except NoSuchElementException : 
                    print('Error can not found element!')   
                    driver.delete_all_cookies()
                    driver.quit()

            # End While        
            # solve send SMS again
            time.sleep(10)
            try:
                sendSMSAgain = driver.find_element(By.XPATH, '/html/body/div[1]/div[2]/div[1]/div/div/div[1]/div[2]/form/div[1]/div[3]/a')
                sendSMSAgain.click()
            except NoSuchElementException:
                print("No need to Send SMS Again!")

            # Resolve to re-enter the phone number and require send SMS
            time.sleep(10)
            try:
                driver.find_element(By.XPATH, '/html/body/div/div/div/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div/div[1]/div/div/div/div/div/div/div/div[2]/div/label/div/div[2]/input').send_keys(phoneNumber)
                sendSMS = driver.find_element(By.XPATH, '/html/body/div/div/div/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div/div[1]/div/div/div/div/div/div/div/div[4]/div/div/div')
                print("Button Send SMS ", sendSMS)
                sendSMS.click()
                # time.sleep(50)
            except NoSuchElementException:
                print("Check point error")

            driver.delete_all_cookies()
            driver.quit()
    
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