import qrcode
import random
import argparse

def main():
    parser = argparse.ArgumentParser(description="Process some input.")
    parser.add_argument("input", type=str, help="The input string")
    args = parser.parse_args()

    # Create a QR code instance
    qr = qrcode.QRCode(
        version=1,
        error_correction=qrcode.constants.ERROR_CORRECT_L,
        box_size=10,
        border=4,
    )

    # Add data to the QR code
    rng_num = random.randrange(100, 1000)
    data = str(rng_num) + str(args.input) + str(luhn(args.input))
    print (data)
    qr.add_data(data)

    # Create the QR code
    qr.make(fit=True)

    img = qr.make_image(fill_color="black", back_color="white")

    with open('qr.png', 'wb') as f:
        img.save(f)

def luhn(value):
    digits = list(map(int,str(value)))
    oddSum = sum(digits[-1::-2])
    evnSum = sum([sum(divmod(2 * d, 10)) for d in digits[-2::-2]])
    return (oddSum + evnSum) % 10 

if __name__ == "__main__":
    main()
