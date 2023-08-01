
# giải captcha
# print("Đang giải captcha")
# async def solve_captcha_sync(num_requests):
#     return [await cap_monster_client.solve_captcha(recaptcha2request) for _ in range(num_requests)]

# async def solve_captcha_async(num_requests):
#     tasks = [asyncio.create_task(cap_monster_client.solve_captcha(recaptcha2request)) 
#              for _ in range(num_requests)]
#     return await asyncio.gather(*tasks, return_exceptions=True)

# api_key='dfb46ea699f1ead2bb5a218f37634279'
# urlWeb = 'https://www.facebook.com/checkpoint/1501092823525282/?next=https%3A%2F%2Fwww.facebook.com%2F'
# site_key = '6Lc9qjcUAAAAADTnJq5kJMjN9aD1lxpRLMnCS2TR'
# # key = os.getenv(api_key)
# client_options = ClientOptions(api_key=api_key)
# cap_monster_client = CapMonsterClient(options=client_options)
# recaptcha2request = RecaptchaV2EnterpriseProxylessRequest(websiteUrl=urlWeb, websiteKey=site_key)
# nums = 1
# sync_start = time.time()
# responses = asyncio.run(solve_captcha_sync(nums))
# driver.switch_to.frame("captcha-recaptcha")
# text = driver.find_element(By.XPATH, '/html/body/div[1]/div/textarea')
# driver.execute_script("arguments[0].style=''", text)
# print("Recaptcha: ", responses[0]['gRecaptchaResponse'])
# driver.execute_script("arguments[0].innerHTML=arguments[1]", text, responses[0]['gRecaptchaResponse'])
# driver.switch_to.default_content()
# driver.find_element(By.XPATH, '/html/body/div/div/div/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div/div[1]/div/div/div/div/div/div/div/div[3]/div/div/div').click()

# time.sleep(5)
# driver.quit()

