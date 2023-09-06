import pandas as pd
import pickle

data_Wet = pd.read_csv("irradiance.csv")

model_pkl_file = "/Challenge/save_model.pkl"

with open(model_pkl_file, 'rb') as file:
    model = pickle.load(file)

data_Wet['DATE_TIME'] = pd.to_datetime(data_Wet['DATE_TIME']).dt.strftime('%d-%m-%Y %H:%M')

#inizia alle 5
pred = model.get_prediction(start=20, end=len(data_Wet) + 19, exog=data_Wet['IRRADIATION'])

pred_futuro = pred.predicted_mean

pred_futuro = pred_futuro.to_frame()

pd.set_option("display.max_columns", None)
pd.set_option("display.max_rows", None)

print(pred_futuro['predicted_mean'].to_string(index=False))

