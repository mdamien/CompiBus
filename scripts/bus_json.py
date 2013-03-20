#!/usr/bin/env python

import json
import pprint
import csv
pp = pprint.PrettyPrinter(indent=4)

lignes = {'name':'Ligne 5',
    'color':(155,122,0),
    'coord':{'lat':49.4149,'lng':2.823056},
    'dirs':{'name':'COMPIEGNE GARE',
            'timetable':[[ str(i)+','+str(j) for i in range (10)] for j in range(10)]
        },
    'days':'week'
    }

#pp.pprint(lignes)
#pp.pprint([[ str(i)+','+str(j) for i in range (10)] for j in range(10)])

def get_timetable(f):
    l = []
    with open(f+'.csv', newline='') as csvfile:
        l = list(csv.reader(csvfile, delimiter=';'))
    return l

line5_dir1 = get_timetable("line5_dir1")
line5_dir2 = get_timetable("line5_dir2")