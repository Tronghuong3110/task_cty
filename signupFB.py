import time
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.chrome.options import Options as ChromeOptions
from selenium.webdriver.common.by import By

profile_path = "C:/Users/ASUS/.hidemyacc/profiles/profile 2"
listPhoneNumber = ['0769045906', '0782357678', '0782353979', '0769119699', '0769118787', '0769108833',
'0769098833' ,'0769097567' , '0769078877', '0769078822', '0769078811']

# open profile created
def option_profile():
    option = ChromeOptions()
    option.add_argument("--user-data-dir=" + profile_path)
    option.add_argument("--profile-directory=Default")
    return option

# fill sign up form
def fillOutForm(driver, phoneNumber):
    driver.find_element(By.NAME, 'lastname').send_keys('Jhon')# Ho
    driver.find_element(By.NAME, 'firstname').send_keys('Tech') # ten
    driver.find_element(By.NAME, 'reg_email__').send_keys(phoneNumber) # so dien thoai
    driver.find_element(By.NAME, 'reg_passwd__').send_keys('123456789@') # mat khau
    driver.find_element(By.NAME, 'birthday_day').send_keys('31') # ngay sinh
    driver.find_element_by_id('month').send_keys('Jan') # thang sinh
    driver.find_element(By.NAME, 'birthday_year').send_keys('2000') # nam sinh
    driver.find_element(By.NAME, 'sex').click() # gioi tinh (nam)

# sign up face
def signupface(driver, phoneNumber):
    driver.get("https://www.facebook.com")
    time.sleep(2)
    try:
        # click dang ki
        driver.find_element(By.XPATH, '/html/body/div[1]/div[1]/div[1]/div/div/div/div[2]/div/div[1]/form/div[5]/a').click() 
    except NoSuchElementException:
        print("Can't found button create account")
    time.sleep(2)
    # fillout form
    fillOutForm(phoneNumber)
    # click submit form sign up
    print("Sign up account")
    driver.find_element(By.NAME, 'websubmit').click()

if __name__ == '__main__':
    for phoneNumber in listPhoneNumber:
        conti = None
        driver = None
        # solve byPass captcha
        while True:
            driver = webdriver.Chrome(options=option_profile()) 
            try:
                signupface(driver, phoneNumber)
                time.sleep(10)
                # click button continue if exists
                try:
                    driver.find_element(By.XPATH, '/html/body/div/div/div/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div/div[1]/div/div/div/div/div/div/div/div[2]/div/div/div[2]/div/div/div').click()
                except NoSuchElementException:
                    print("Can't not button continue")
                # Bypass captcha
                print('Resolve captcha')
                time.sleep(10)
                driver.switch_to.frame('captcha-recaptcha')

                # click button to Bypass captcha use api of 2 captcha
                resolveCaptcha = driver.find_element(By.XPATH, '/html/body/div[1]/div[2]/div[2]')
                resolveCaptcha.click()
                time.sleep(20)
                driver.switch_to.default_content()

                # click continue when Bypass captcha success
                conti = driver.find_element(By.XPATH, '/html/body/div/div/div/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div/div[1]/div/div/div/div/div/div/div/div[3]/div/div/div')
                print("Continue ", conti)
                conti.click()
                break

            # Can not found any element in website
            except NoSuchElementException: 
                print('Error!')   
                driver.delete_all_cookies()
                driver.quit()

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

            # click reSend SMS
            time.sleep(10)
            sendSMSAgain = driver.find_element(By.XPATH, '/html/body/div/div/div/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div/div[1]/div/div/div/div/div/div/div/div[3]/div/div[1]/span/div')
            sendSMSAgain.click()
        except NoSuchElementException:
            print("Check point error")
        driver.delete_all_cookies()
        driver.quit()

#List số mới
# 0766041740 ==> xong
# 0766201720 ==> xong
# 0762239340 ==> xong
# 0769038167 ==> xong
# 0766066871 ==> xong
# 0766237972 ==> check point
# 0766039346 ==> xong
# 0766145746 ==> xong
# 0768250752 ==> xong
# 0766048671 ==> xong

# 192.168.100.4:55331
# 192.168.100.4:55333
# 192.168.100.4:55335
# 192.168.100.4:55337
# 192.168.100.4:55339

# 0769065761
# 0769045906
# 0782357678
# 0782353979
# 0769119699
# 0769118787
# 0769108833
# 0769098833 
# 0769097567 
# 0769078877
# 0769078822
# 0769078811