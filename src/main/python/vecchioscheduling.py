import xpress as xp
import numpy as np
import math

xp.init("C:\\xpressmp\\bin\\xpauth.xpr")

model = xp.problem(name="Guadagno")

N = 12
M = 5

#Produzione = np.array(N)
Produzione = [10,12, 14, 16, 20, 20, 16, 14, 12, 10, 9, 8]
#ConsumoOggetto = np.array(M)
ConsumoOggetto = [0.5, 0.5, 0.5, 0.5, 1]
#Costi = np.array(N)
Costi = [0.5,0.5,0.5,0.6, 0.8, 0.6, 0.5, 0.5,0.5, 0.5, 0.5, 0.5,]
#Ricavi = np.array(N)
Ricavi = [0.4, 0.5, 0.5, 0.6, 0.7, 0.5, 0.4, 0.4, 0.4, 0.4, 0.3, 0.3]
#Disp = np.array(N,M)
Disp = [
    [1,0,0,0,0],
    [1,1,0,0,1],
    [1,1,1,0,1],
    [1,1,1,1,1],
    [0,1,0,1,1],
    [0,1,0,1,1],
    [1,1,0,1,1],
    [1,0,1,1,1],
    [1,0,1,1,1],
    [0,0,1,0,1],
    [0,0,1,0,1],
    [0,0,0,0,1]
]
#Durata = np.array(M)
Durata = [3,4,2,4,6]
#Incentivo = np.array(N)
Incentivo = [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]





allocazione = xp.vars(N, M, name="allocazione", vartype =xp.binary)

model.addVariable(allocazione)

def objectiveFunction(cons,prod,costo, ricavo, incentivo):
    return np.where(cons>prod,costo*(prod - cons) + incentivo*prod, ricavo*(prod - cons) + incentivo*cons)

def consumototale(i):
    return xp.Sum(ConsumoOggetto[j] * allocazione[i][j] for j in range(M))




#vincolo allocazione
model.addConstraint(allocazione[i][j] <= Disp[i][j] for i in range(N) for j in range(M))

#vincolo durata

model.addConstraint(xp.Sum([allocazione[i][j] for i in range(N)]) == Durata[j] for j in range(M))

#vincolo continuità

for j in range(M):
    for i in range(N):
        model.addConstraint(allocazione[i][j] + allocazione[k][j] <= 1 for k in range(i + Durata[j], N))



#funzione obiettivo costi uguali
objective = xp.Sum(Costi[i] * (Produzione[i] - consumototale(i)) for i in range(N))

#funzione obiettivo con costo diverso da ricavi e energia shared
#objective = (xp.Sum(xp.user(objectiveFunction, consumototale(i), Produzione[i], Costi[i], Ricavi[i], Incentivo[i]) for i in range(N)))

#funzione obiettivo con scarto medio
#objective = xp.Sum(xp.user(np.abs, Produzione[i] - consumototale(i)) for i in range(N))


model.setObjective(objective, sense = xp.maximize)

model.solve()
for i in range(N):
    for j in range(M):
        print (model.getSolution(allocazione[i][j]), end = " ")
    print("\n")