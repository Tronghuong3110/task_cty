from selenium.webdriver.chrome.options import Options as ChromeOptions

# create option to open profile path
class Option:
    def __init__(self, profilePath):
        self.profilePath = profilePath

    def createOption(self):
        option = ChromeOptions()
        option.add_argument("--user-data-dir=" + self.profilePath)
        option.add_argument("--profile-directory=20")
        return option