import xpress as xp
import numpy as np
import sys
import json
import math

model = xp.problem(name="Guadagno")

N = int(sys.argv[1])
M = int(sys.argv[2])

#Produzione = np.array(N)
Produzione = json.loads(sys.argv[3])

#ConsumoOggetto = np.array(M)
ConsumoOggetto = json.loads(sys.argv[4])

#Disp = np.array(N,M)
Disp = json.loads(sys.argv[8])

#Durata = np.array(M)
Durata = json.loads(sys.argv[9])

#Interrompibile = np.array(M)
Interrompibile = json.loads(sys.argv[10])


allocazione = xp.vars(N, M, name="allocazione", vartype =xp.binary)
differenza = [xp.var(name=f"differenza-{i}", vartype =xp.continuous) for i in range(N)]

model.addVariable(allocazione)
model.addVariable(differenza)

#vincolo allocazione
model.addConstraint(allocazione[i][j] <= Disp[i][j] for i in range(N) for j in range(M))

#vincolo durata
model.addConstraint(xp.Sum([allocazione[i][j] for i in range(N)]) == Durata[j] for j in range(M))

#vincolo continuità

for i in range(N):
    for j in range(M):
        if(Interrompibile[j] == 0):
            model.addConstraint(allocazione[i][j] + allocazione[k][j] <= 1 for k in range(i + Durata[j], N))
    model.addConstraint(differenza[i] >= (Produzione[i] - xp.Sum(ConsumoOggetto[j] * allocazione[i][j] for j in range(M))))
    model.addConstraint(differenza[i] >= (xp.Sum(ConsumoOggetto[j] * allocazione[i][j] for j in range(M)) - Produzione[i]))


objective = xp.Sum(differenza[i] for i in range(N))

model.setObjective(objective, sense = xp.minimize)

model.solve()



print("#\n")

for j in range(M):
    for i in range(N):
        print (model.getSolution(allocazione[i][j]), end = "\n")

