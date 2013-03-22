#!/usr/bin/env python3

import json
import pprint
import csv
pp = pprint.PrettyPrinter(indent=4)

#pp.pprint(lignes)
#pp.pprint([[ str(i)+','+str(j) for i in range (10)] for j in range(10)])

def get_timetable(f):
    l = []
    with open(f+'.csv') as csvfile:
        l = list(csv.reader(csvfile, delimiter=';'))
    return l
    
def print_line(line):
	for row in line:
		for p in row:
			print(p),
		print(" ")

line5_dir1 = get_timetable("line5_dir1")
line5_dir2 = get_timetable("line5_dir2")
line1_dir1 = get_timetable("line1_dir1")
line1_dir2 = get_timetable("line1_dir2")
line2_dir1 = get_timetable("line2_dir1")
line2_dir2 = get_timetable("line2_dir2")
line34_dir1 = get_timetable("line34_dir1")
line34_dir2 = get_timetable("line34_dir2")
line6_dir1 = get_timetable("line6_dir1")
line6_dir2 = get_timetable("line6_dir2")
line7_dir1 = get_timetable("line7_dir1")
line7_dir2 = get_timetable("line7_dir2")


line5 = {'name':'Ligne 5',
    'color':(155,122,0),
    'dirs':(line5_dir1,line5_dir2)
    }
line1 = {'name':'Ligne 1',
    'color':(155,0,147),
    'dirs':(line1_dir1,line1_dir2)
    }
line2 = {'name':'Ligne 2',
    'color':(250,10,50),
    'dirs':(line2_dir1,line2_dir2)
    }
line34 = {'name':'Ligne 3/4',
    'color':(40,200,70),
    'dirs':(line2_dir1,line2_dir2)
    }
line6 = {'name':'Ligne 6',
    'color':(42,10,0),
    'dirs':(line6_dir1,line6_dir2)
    }
line7 = {'name':'Ligne 7',
    'color':(57,10,100),
    'dirs':(line7_dir1,line7_dir2)
    }

lines = line5,line1,line2,line34,line6,line7

#pp.pprint(lines)

f = open("data.json","w")
json.dump(lines,f,indent=2,sort_keys=True)

print("Done!")
