import os

def generate_sql(stadium_id, output_dir):
    sections = range(1, 5)  # 4 sections
    floors = ['A', 'B']  # Floors A and B
    rows_floor_a = [chr(i) for i in range(ord('A'), ord('M'))]  # Rows A to L for floor A
    rows_floor_b = [chr(i) for i in range(ord('A'), ord('P'))]  # Rows A to O for floor B
    
    # Define columns for each section
    columns = {
        1: range(1, 261),  # Columns 1 to 260 for sections 1 and 4
        2: range(1, 161),  # Columns 1 to 160 for section 2
        3: range(1, 161),  # Columns 1 to 160 for section 3
        4: range(1, 261)   # Columns 1 to 260 for section 1 and 4
    }

    sql_statements = []
    for section in sections:
        for floor in floors:
            if floor == 'A':
                rows = rows_floor_a
            else:
                rows = rows_floor_b
            seat_section = f"{section}{floor}"
            for row in rows:
                for column in columns[section]:
                    seat_number = f"{row}{column}"
                    seat_side = 'HT' if section in [1, 2, 3] else 'AT'
                    sql = f"INSERT INTO seats (seat_stadium_id, seat_section, seat_number, seat_side) VALUES ({stadium_id}, '{seat_section}', '{seat_number}', '{seat_side}');"
                    sql_statements.append(sql)
    
    # Ensure the output directory exists
    os.makedirs(output_dir, exist_ok=True)
    
    output_file = os.path.join(output_dir, f'stadium_{stadium_id}_seats_inserts.sql')
    with open(output_file, 'w') as file:
        for statement in sql_statements:
            file.write(statement + '\n')
    
    print(f"SQL commands for Stadium {stadium_id} have been written to {output_file}")

# Example usage
output_dir = 'stadium_sql_files'
generate_sql(102, output_dir)  # Example for Stadium ID 102
