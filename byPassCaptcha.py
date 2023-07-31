class ByPassCaptcha:
    def __init__(self, driver) :
        self.driver = driver

    def displayTextArea(self):
        textAres = self.driver.find_element_by_id('g-recaptcha-response')
        textAres.get_attribuite('style', '')