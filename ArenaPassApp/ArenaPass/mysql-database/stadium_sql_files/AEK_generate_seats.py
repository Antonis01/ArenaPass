sections = range(1, 46)  # Sections 1 to 45
floors = ['A', 'B']  # Floors A and B
rows = [chr(i) for i in range(ord('A'), ord('Q'))]  # Rows A to P
columns = range(1, 21)  # Columns 1 to 20

# Function to generate SQL statements
def generate_sql(seat_stadium_id, output_file):
    sql_statements = []
    for section in sections:
        for floor in floors:
            seat_section = f"{section}{floor}"
            for row in rows:
                for column in columns:
                    seat_number = f"{row}{column}"
                    seat_side = 'AT' if section <= 15 else 'HT'
                    sql = f"INSERT INTO seats (seat_stadium_id, seat_section, seat_number, seat_side) VALUES ({seat_stadium_id}, '{seat_section}', '{seat_number}', '{seat_side}');"
                    sql_statements.append(sql)
    
    with open(output_file, 'w') as file:
        for statement in sql_statements:
            file.write(statement + '\n')

# Example usage
seat_stadium_id = 101  # Example stadium ID
output_file = 'seats_inserts.sql'
generate_sql(seat_stadium_id, output_file)

print(f"SQL commands have been written to {output_file}")
