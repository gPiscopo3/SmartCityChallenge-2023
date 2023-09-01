import os
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

import statsmodels.api as sm
from statsmodels.tsa.statespace.sarimax import SARIMAX



file = "../../../../../Downloads/NuovoDatasetLungo.csv"
data = pd.read_csv(file, sep=',', parse_dates=[0])



#data['DATE_TIME'] = pd.to_datetime(data['DATE_TIME'], infer_datetime_format=True, utc=True, errors='ignore')

#powerZ1 = pd.Series(data=data['DC_POWER'].values, index=data['INDEX'].values )
data.set_index('INDEX', inplace=True)

#powerZ1_dec = powerZ1['2020-05-15 00:00:00':]

fas = sm.tsa.acf(data['DC_POWER'], nlags=300)
fap = sm.tsa.pacf(data['DC_POWER'], nlags=300)

fig, axs = plt.subplots(1, 2, figsize=(15,7))
fig.suptitle('Time series correlograms', y=1)
axs[0].stem(fas)
axs[0].set_title('ACF')
axs[0].set_xlabel('n_lags')
axs[0].grid(True)
axs[1].stem(fap)
axs[1].set_title('PACF')
axs[1].set_xlabel('n_lags')
axs[1].grid(True)
plt.tight_layout()
plt.show()

#MMd = powerZ1_dec.rolling(144, center=True).mean()
#STDd = powerZ1_dec.rolling(144, center=True).std()

##plt.figure(figsize=(18,9))
#plt.plot(powerZ1_dec, linestyle='-', color='red', alpha=0.4, label='data')
#plt.plot(MMd, linestyle='-', color='b', label='Moving average (1d)')
#plt.plot(STDd, linestyle='--', color='b', label='STD (1d)')
#plt.ylabel('Power Consumption')
#plt.title('Moving average', y=1)
#plt.legend()
#plt.show()


#decomposition
#descomposicion_d = seasonal_decompose(powerZ1_dec, model='aditive', period=144)

#plt.rcParams.update({'figure.figsize': (12,10)})
#descomposicion_d.plot().suptitle('Time series decomposition', y=1.01)
#plt.show()

#dif = sm.tsa.statespace.tools.diff(powerZ1, k_diff=0, k_seasonal_diff=0, seasonal_periods=96)

#dif.plot()
#plt.title('Time series differentiation')
#plt.show()


#fas_dif = sm.tsa.acf(dif, nlags=200)
#fap_dif = sm.tsa.pacf(dif, nlags=200)

#fig, axs = plt.subplots(1, 2, figsize=(15,7))
#fig.suptitle('Differenced time series correlograms', y=1)
#axs[0].stem(fas_dif)
#axs[0].set_title('ACF')
#axs[0].set_xlabel('n_lags')
#axs[0].grid(True)
#axs[1].stem(fap_dif)
#axs[1].set_title('PACF')
#axs[1].set_xlabel('n_lags')
#axs[1].grid(True)
#plt.tight_layout()
#plt.show()

s = 96
p = 1
q = 1
d = 1
P = 3
Q = 1
D = 1

#train = powerZ1_dec[:'2017-12-28 23:50:00']
#test = powerZ1_dec['2017-12-29 00:00:00':]

#model = ARIMA(train, order=(p,d,q), seasonal_order=(P,D,Q,s))
#model = model.fit(low_memory=True)
#print('SARIMA model summary\n', model.summary())

#futuro = pd.date_range(periods=196, freq='15T')
exogenous = data['IRRADIATION']
DC_POWER = data['DC_POWER']

model = SARIMAX(endog=DC_POWER[:792], order=(p,d,q), exog=exogenous[:792], seasonal_order=(P,D,Q,s))
model = model.fit(low_memory=False)
print('SARIMA model summary\n', model.summary())



#pred = model.get_prediction(end=len(data['DC_POWER'])+196-1, exog=exogenous[1:197])
pred = model.get_prediction(end=len(data['DC_POWER'])-1, exog=exogenous[793:889])



pred_train = pred.predicted_mean[:792]
pred_futuro = pred.predicted_mean[793:]
pred_conf_int = pred.conf_int()
print(pred_conf_int.head())
pred_conf_int = pd.concat([data,pred_conf_int])

plt.figure(figsize=(18,9))
plt.plot(data['DC_POWER'], color='red', linestyle='-', label='Data')
plt.plot(pred_train, color='green', linestyle='-', label='Current pred')
plt.plot(pred_futuro, color='blue', linestyle='-', label='Future pred')

plt.fill_between(pred_conf_int.index, pred_conf_int['lower DC_POWER'], pred_conf_int['upper DC_POWER'], color='k', alpha=0.1, label='conf. int.', edgecolor=None)
#plt.ylim(-50, 350)
plt.xlabel('Datetime')
plt.ylabel('DC_POWER')
plt.title('Prediction DC_POWER')
plt.legend()
plt.show()