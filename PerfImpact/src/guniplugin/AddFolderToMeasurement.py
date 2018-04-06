import os

#Getting folder
folder = input("Type the path of the folder within the mcFunctions will be added to measurement:\n")
os.system("cd {}".format(folder))

os.system("pause")

current = os.pipen("cd")

os.system("pause")

while current != folder:
	folder = input("\nWrong folder path, please retype it:\n")
	os.system("cd {}".format(folder))
	current = os.pipen("cd")

files = os.pipen("dir")
print(files)

os.system("pause")