from fpdf import FPDF
import sys
import os

class PDF(FPDF):
    def header(self):
        self.set_font('Arial', 'B', 12)
        self.cell(0, 10, 'Arena Pass', 0, 1, 'C')

    def footer(self):
        self.set_y(-15)
        self.set_font('Arial', 'I', 8)
        self.cell(0, 10, f'Page {self.page_no()}', 0, 0, 'C')

def create_arena_pass_pdf(output_path, pass_id, name, surname, id_doc_number, reservation_id):
    pdf = PDF()
    pdf.add_page()

    # Determine the directory of the current script
    script_dir = os.path.dirname(os.path.abspath(__file__))

    # Construct absolute paths for the images
    first_image_path = os.path.join(script_dir, "images", "ArenaPass.png")

    # Adjust the path for qr.png which is in the parent folder of src
    parent_dir = os.path.dirname(script_dir)
    qr_image_path = os.path.join(parent_dir, "qr.png")

    # Add the first image (3 times smaller)
    first_image_width = 500 / 3 / 72 * 72  # Convert pixels to points and then to the desired size
    first_image_height = 197 / 3 / 72 * 72  # Convert pixels to points and then to the desired size
    pdf.image(first_image_path, x=10, y=20, w=first_image_width, h=first_image_height)

    # Add QR code placeholder
    pdf.set_y(20 + first_image_height + 10)  # Adjust y position based on the first image size
    pdf.set_x(10)
    pdf.set_font('Arial', 'B', 12)
    # pdf.cell(0, 10, 'QR Code Placeholder', 0, 1)

    # Add the second image (4 times smaller)
    second_image_width = 290 / 4 / 72 * 72  # Convert pixels to points and then to the desired size
    second_image_height = 290 / 4 / 72 * 72  # Convert pixels to points and then to the desired size
    pdf.image(qr_image_path, x=10, y=20 + first_image_height + 30, w=second_image_width, h=second_image_height)

    # Add details
    pdf.set_y(20 + first_image_height + 30 + second_image_height + 10)  # Adjust y position based on the second image size
    pdf.set_font('Arial', '', 12)
    pdf.cell(0, 10, f'Pass ID: {pass_id}', 0, 1)
    pdf.cell(0, 10, f'Name: {name}', 0, 1)
    pdf.cell(0, 10, f'Surname: {surname}', 0, 1)
    pdf.cell(0, 10, f'ID Document Number: {id_doc_number}', 0, 1)
    pdf.cell(0, 10, f'Reservation ID: {reservation_id}', 0, 1)

    pdf.output(output_path)

if __name__ == '__main__':
    if len(sys.argv) != 7:
        print("Usage: python script.py <output_path> <pass_id> <name> <surname> <id_doc_number> <reservation_id>")
        sys.exit(1)

    output_path = sys.argv[1]
    pass_id = sys.argv[2]
    name = sys.argv[3]
    surname = sys.argv[4]
    id_doc_number = sys.argv[5]
    reservation_id = sys.argv[6]

    create_arena_pass_pdf(output_path, pass_id, name, surname, id_doc_number, reservation_id)
