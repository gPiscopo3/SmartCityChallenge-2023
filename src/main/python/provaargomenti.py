import sys
import json

print(sys.argv[1])
array=json.loads(sys.argv[1])
matrix = json.loads(sys.argv[2])

for i in array:
    print(i)

for riga in matrix:
    for elemento in riga:
        print(elemento)
    print("\n")