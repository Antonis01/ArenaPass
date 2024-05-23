import qrcode
import random

# Create a QR code instance
qr = qrcode.QRCode(
    version=1,
    error_correction=qrcode.constants.ERROR_CORRECT_L,
    box_size=10,
    border=4,
)

# Add data to the QR code
rng_num = random.randrange(1000000000, 10000000000)
data = "antonis" + " anastasiou" + str(rng_num)
qr.add_data(data)

# Create the QR code
qr.make(fit=True)

# Create an image from the QR code instance
img = qr.make_image(fill_color="black", back_color="white")

# Save the image
with open('qr.jpg', 'wb') as f:
    img.save(f)
