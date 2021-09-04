from excelUtils import ExcelHandler, find_ext
import os 
import requests

machine = 10

check = input("Did you changed the machine and the sheet and interval?")
if(check != 'y'):
	exit()

table_CT = {"electric": 1, "mecanic": 2, "pneumatic":3, "hidraulic": 4}
table_Sub = {"Carcasa masina": 1, "Turbina": 2, "ELEVATOR \"E 200-S\"":3,
				"SHOT SEPARATOR":4, "MACHINE BODY":5,"LOADING SYSTEM":6,
				"BEDS TRANSMISSION": 7, "UNLOADING SYSTEM":8, "RODS CHECKING DEVICE":9,
				"DUST COLLECTOR UNIT": 10, "PNEUMATIC UNIT":11,
				"SMT 19 Siemens": 12, "Masa": 13, "Masa turela": 14,
				"Transmisie": 15, "Papusa mobila": 16, "Universal si cilindru":17,
				"Axa Z": 18, "Encoder Liniar axa Z": 19, "Encoder Liniar axa X": 20,
				"Alimentare bare":21 , "Linete":22, "Ax":23, 
				"Statie hidraulica":24, "Carcasa":25, "Conveior span":26,
				"Carcasa Axa Z":27 , "Dulap electric":28, "Carcasa Axa X":29,
				"Lubrifiere CNC":30 , "Lubrifiere alimentare bare":31, "Pneumatic":32,
				"Scule":33, "Universal":34, "Lunete": 35, "Macara materie prima": 36,
				"Macara Forja": 37, "Macara Sablare": 38, "Macara Pachetizare": 39,
				"Macara Expeditie": 40, "Macara Cuptor": 41, "Articulatie": 42,
				"Actionare masina": 43, "Frana": 44, "Berbec glisor": 45, 
				"Berbec Matriter": 46, "Poanson": 47, "Pat alimentare":48,
				"Accelerator bara": 49, "Lift port bara": 50, "Pat evacuare": 51,
				"Colector Bare rebut": 52, "Arzator superior 7724.16-1.3.0": 53,
				"Arzator inferior 7724.16-1.2.0": 54, "INTERIOR CUPTOR": 55,
				"PAT RACIRE": 56, "CALE IESIRE": 57, "PC LOCAL": 58, 
				"RAMPA ALIMENTARE GAZ 7724.16-1.1.0": 59,"RAMPA ALIMENTARE AER 7724.16-0.0.0ST1": 60,
				"PUPITRU LOCAL EVACUARE":61, "PUPITRU LOCAL INTRARE": 62, "Dulap electric": 63,
				"Pupitru central":64, "Exhaustor": 65, "Transport ": 66,
				"ENCODER": 67, "TURELA T10":68, "TURELA AXA 'X'":69, 
				"TURELA AXA 'Z'":70, "UNIVERSAL": 71, "BLOCUL HIDRAULIC": 72,
				"ANSAMBLUL TRAVERSA": 73, "DULAPUL ELECTRIC": 74, 
				"PANOUL DE  OPERATORI": 75,"DULAP ELECTRIC HANDLING": 76,

				 }


excelFileName = "test.xlsx"
excel_path = os.path.join(os.getcwd(), excelFileName)

handler = ExcelHandler(excel_path, sheet="CNC PSH")

columns, table_data, refs = handler.read_excel_table(isTable=False, table_range="B2:H335")

#choose the interval from which we take the data
intervalToTake = "B3:H335"
offset = -3 #for some reason we need an offset 
letter1, number1, letter2, number2 = handler.parseRef(intervalToTake)
n1 = int(number1) + offset
n2 = int(number2) + offset +1

for i in range(n1,n2):
	subansamblu = table_data["Echipament"][i]
	componentType = table_data["Sursa"][i].lower()
	nume = table_data["Piesa"][i]
	print(subansamblu)
	print(componentType)
	print(nume)


	sid = table_Sub[subansamblu]
	ctid = table_CT[componentType]

	pload = {'name':nume,'subansamblu':sid,'componentType':ctid}
	url = "http://127.0.0.1:8080/component/create/"+str(machine)

	r = requests.post(url, data = pload)
	print(r)

