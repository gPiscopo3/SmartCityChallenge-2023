import pandas as pd
from statsmodels.tsa.statespace.sarimax import SARIMAX

##### VARIABILI SARIMAX #####
s = 96
p = 1
q = 1
d = 1
P = 3
Q = 1
D = 1


#############################
data_Gen = pd.read_csv("Gen_data_aggregate_plant2.csv")
# data_Gen = data_Gen.loc[data_Gen['SOURCE_KEY'] == id_plant]

data_Wet = pd.read_csv("Plant_2_Weather_Sensor_Data.csv")

# Converte la colonna data_Gen nel formato 'dd-mm-yyyy HH:MM'
data_Gen['DATE_TIME'] = pd.to_datetime(data_Gen['DATE_TIME'], format='%Y-%m-%d %H:%M').dt.strftime('%d-%m-%Y %H:%M')

# Converte la colonna data_Wet nel formato 'dd-mm-yyyy HH:MM'
data_Wet['DATE_TIME'] = pd.to_datetime(data_Wet['DATE_TIME']).dt.strftime('%d-%m-%Y %H:%M')

df_merged = pd.merge(data_Gen, data_Wet, on='DATE_TIME', how='inner')
#to_delete = ['PLANT_ID_x', 'SOURCE_KEY_x', 'SOURCE_KEY_y', 'PLANT_ID_y']
#df_merged = df_merged.drop(to_delete, axis=1)
df_merged = df_merged.set_index('DATE_TIME')

print(df_merged.columns)
print(df_merged.head())
print(df_merged.shape)

data_train = df_merged[:704]
data_test = df_merged[704:800]

model = SARIMAX(endog=data_train['AC_POWER'], order=(p, d, q), exog=data_train['IRRADIATION'],
                seasonal_order=(P, D, Q, s))
model = model.fit(low_memory=False)
print('SARIMA model summary\n', model.summary())

model.save('save_model.pkl')
