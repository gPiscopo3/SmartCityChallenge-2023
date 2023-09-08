import xpress as xp
import numpy as np
import sys
import json
import math

xp.init("C:\\xpressmp\\bin\\xpauth.xpr")

model = xp.problem(name="Guadagno")

N = int(sys.argv[1])
M = int(sys.argv[2])

#Produzione = np.array(N)
Produzione = json.loads(sys.argv[3])

#ConsumoOggetto = np.array(M)
ConsumoOggetto = json.loads(sys.argv[4])

#Costi = np.array(N)
Costi = json.loads(sys.argv[5])

#Ricavi = np.array(N)
Ricavi = json.loads(sys.argv[6])

#Incentivo = np.array(N)
Incentivo = json.loads(sys.argv[7])

#Disp = np.array(N,M)
Disp = json.loads(sys.argv[8])

#Durata = np.array(M)
Durata = json.loads(sys.argv[9])

#Interrompibile = np.array(M)
Interrompibile = json.loads(sys.argv[10])



allocazione = xp.vars(N, M, name="allocazione", vartype =xp.binary)

model.addVariable(allocazione)

def objectiveFunction(cons,value,costo, ricavo, tariffaOraria):
    return np.where(cons>value,costo*(value - cons) + tariffaOraria*value, ricavo*(value - cons) + tariffaOraria*cons)

def consumototale(i):
    return xp.Sum(ConsumoOggetto[j] * allocazione[i][j] for j in range(M))




#vincolo allocazione
model.addConstraint(allocazione[i][j] <= Disp[i][j] for i in range(N) for j in range(M))

#vincolo durata

model.addConstraint(xp.Sum([allocazione[i][j] for i in range(N)]) == Durata[j] for j in range(M))

#vincolo continuità

for j in range(M):
    for i in range(N):
        if(Interrompibile[j] == 0):
            model.addConstraint(allocazione[i][j] + allocazione[k][j] <= 1 for k in range(i + Durata[j], N))



#funzione obiettivo costi uguali
objective = xp.Sum(Costi[i] * (Produzione[i] - consumototale(i)) for i in range(N))

#funzione obiettivo con costo diverso da ricavi e energia shared
#objective = (xp.Sum(xp.user(objectiveFunction, consumototale(i), Produzione[i], Costi[i], Ricavi[i], Incentivo[i]) for i in range(N)))

#funzione obiettivo con scarto medio
#objective = xp.Sum(xp.user(np.abs, Produzione[i] - consumototale(i)) for i in range(N))


model.setObjective(objective, sense = xp.maximize)

model.solve()

print("#\n")


for j in range(M):
    for i in range(N):
        print (model.getSolution(allocazione[i][j]), end = "\n")

