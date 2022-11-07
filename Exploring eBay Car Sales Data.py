#!/usr/bin/env python
# coding: utf-8

# # Project 1: Exploring eBay Car Sales Data
# Target: clean the data and analyse the included used car listings
# 
# Source: https://data.world/data-society/used-cars-data
# 
# #### I. Opening data

# In[1]:


import pandas as pd
import numpy as np

autos = pd.read_csv('autos.csv', encoding = 'Latin-1')


# In[2]:


autos


# In[3]:


autos.info()


# In[4]:


autos.head()


# #### II.Cleaning data

# In[5]:


autos.columns


# In[6]:


def camel_snake(strings):
    res = [string[0].lower()]
    for char in strings[1:]:
        if char in ('ABCDEFGHIJKLMNOPQRSTUVWXYZ'):
            res.append('_')
            res.append(char.lower())
        else:
            res.append(char)
    return ''.join(res) # join the character in the list


# In[7]:


new_columns = []
for string in autos.columns:
    column_snake = camel_snake(string)
    new_columns.append(column_snake)
autos.columns = new_columns


# In[8]:


autos.columns


# In[9]:


autos.rename({'year_of_registration':'registration_year', 'odometer':'kilometer', 'not_repaired_damage':'unrepaired_damage', 'date_created':'ad_created'},axis = 1, inplace = True) 


# In[10]:


autos.columns


# In[11]:


autos.describe(include='all')


# In[12]:


autos.drop(['seller','offer_type','nr_of_pictures'], axis = 1, inplace = True)


# In[13]:


autos['price'] = autos['price'].str.replace('$','')
autos['price'] = autos['price'].str.replace(',','').astype(float)
autos['kilometer'] = autos['kilometer'].str.replace('km','')
autos['kilometer'] = autos['kilometer'].str.replace(',','').astype(float)


# In[14]:


autos.head(2)


# In[15]:


autos['gearbox'].unique()


# In[16]:


autos['unrepaired_damage'].unique()


# In[17]:


autos['fuel_type'].unique()


# In[18]:


germany_dict = {'manuell':'manual', 
                'automatik':'automatic',
                'nein':'no',
                'ja':'yes',
                'lpg':'lpg',
                'benzin':'benzine',
                'diesel':'diesel',
                'cng':'cng',
                'hybrid':'hybrid',
                'elektro':'electro',
                'andere':'other'}
autos['gearbox'] = autos['gearbox'].map(germany_dict)
autos['gearbox'].unique


# In[19]:


autos['fuel_type'] = autos['fuel_type'].map(germany_dict)
autos['fuel_type'].unique


# #### III. Data Analysis
# 
# Scientific notation

# In[20]:


pd.set_option('display.float_format', lambda x: '%.2f' % x)


# In[21]:


autos.describe()


# In[22]:


#sort_values and sort_index
biggest_prices = autos['price'].value_counts().sort_index(ascending = False).head(10)
biggest_prices


# In[23]:


autos = autos.loc[autos['price'].between(0,999999),:]


# In[24]:


autos['price'].describe()


# In[25]:


autos = autos.loc[autos['kilometer'].between(0,999999),:]


# In[26]:


biggest_kilometer = autos['kilometer'].value_counts(normalize=True).sort_index(ascending = False).head(10)
biggest_kilometer


# In[27]:


autos.columns


# In[28]:


date_crawled = autos['date_crawled'].str[:10].value_counts(normalize=True).sort_index(ascending = False).head(10)
date_crawled


# In[29]:


ad_created = autos['ad_created'].str[:10].value_counts(normalize=True).sort_index(ascending = False).head(10)
ad_created


# In[30]:


autos['registration_year'].describe()


# In[31]:


regis_year = autos['registration_year'].value_counts().sort_index().head(10)
regis_year


# In[32]:


autos[autos['registration_year'] == 1950]


# In[33]:


autos = autos[autos['registration_year'].between(1927,2016)]


# In[34]:


autos['registration_year'].describe()


# In[35]:


autos['brand'].unique()


# In[36]:


brands = autos['brand'].value_counts(normalize=True).head(10)
brands


# In[37]:


top_brands = brands[brands>0.05].index
top_brands


# In[38]:


top_brand_price = {}
for brand_name in top_brands:
    avg_price = autos[autos['brand'] == brand_name]['price'].mean()
    top_brand_price[brand_name] = round(avg_price)
top_brand_price


# In[39]:


top_brand_km = {}
for brand_name in top_brands:
    avg_km = autos[autos['brand'] == brand_name]['kilometer'].mean()
    top_brand_km[brand_name] = round(avg_km)
top_brand_km


# In[40]:


price_serie = pd.Series(top_brand_price)
km_serie = pd.Series(top_brand_km)
price_km = pd.DataFrame(price_serie,columns = ['price_series'])
price_km['km_serie'] = km_serie
price_km


# In[41]:


brand_model = autos.groupby('brand').model.value_counts()
brand_model


# In[42]:


brand_model_audi = autos[autos['brand'] == 'audi'].groupby('brand').model.value_counts()
brand_model_audi


# In[43]:


autos['unrepaired_damage'].value_counts(dropna = False)


# In[44]:


max_unrepaired = autos.groupby('unrepaired_damage')['price'].max()
max_unrepaired


# In[45]:


autos['kilometer'].unique()


# 5 group of value (in 1000km) : '5-30', '30-40', '40-60', '60-100', '100-150'

# In[46]:


km_5_30 = autos[(autos['kilometer'] >= 5000) & (autos['kilometer'] < 30000)]['price'].mean()
km_5_30


# In[47]:


km_30_40 = autos[(autos['kilometer'] >= 30000) & (autos['kilometer'] < 40000)]['price'].mean()
km_30_40


# In[48]:


km_40_60 = autos[(autos['kilometer'] >= 40000) & (autos['kilometer'] < 60000)]['price'].mean()
km_40_60


# In[49]:


km_60_100 = autos[(autos['kilometer'] >= 60000) & (autos['kilometer'] < 100000)]['price'].mean()
km_60_100


# In[50]:


km_100_150 = autos[(autos['kilometer'] >= 100000) & (autos['kilometer'] <= 150000)]['price'].mean()
km_100_150


# #### IV.Conclusion
# -The difference of average prices between non_damaged and damaged cars are on average 3.5 times cheaper
# 
# -Only 6 brands count more than 6% of the the total number of cars each
