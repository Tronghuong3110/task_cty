from selenium import webdriver
import time
from selenium.webdriver.common.by import By

driver = webdriver.Chrome()

driver.get("https://www.facebook.com/")
# time.sleep(1)
driver.find_element(By.XPATH, '/html/body/div[1]/div[1]/div[1]/div/div/div/div[2]/div/div[1]/form/div[5]/a').click() # click dang ki
time.sleep(2)
userName = driver.find_element(By.NAME, 'lastname').send_keys('Nguyen')# Ho
time.sleep(2)
driver.find_element(By.NAME, 'firstname').send_keys('Trong') # ten
time.sleep(2)
driver.find_element(By.NAME, 'reg_email__').send_keys('0398565499') # so dien thoai
time.sleep(2)
driver.find_element(By.NAME, 'reg_passwd__').send_keys('123456789@') # mat khau
time.sleep(2)
driver.find_element(By.NAME, 'birthday_day').send_keys('31') # ngay sinh
time.sleep(2)
driver.find_element_by_id('month').send_keys('Jan')
time.sleep(2)
driver.find_element(By.NAME, 'birthday_year').send_keys('2000') # nam sinh
time.sleep(2)
driver.find_element(By.NAME, 'sex').click() # gioi tinh (nam)
time.sleep(1)
print("Đang đăng kí tài khoản")
driver.find_element(By.NAME, 'websubmit').click()
