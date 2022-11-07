#!/usr/bin/env python
# coding: utf-8

# The goal of our analysis is to determine a few indicators of heavy traffic on I-94
# 
# Source: https://archive.ics.uci.edu/ml/datasets/Metro+Interstate+Traffic+Volume
# 
# #### I.Opening data:

# In[1]:


import pandas as pd
i_94 = pd.read_csv('Metro_Interstate_Traffic_Volume.csv')
import warnings
warnings.filterwarnings('ignore')
i_94.columns


# In[2]:


i_94.head(2)


# In[3]:


i_94.tail(2)


# In[4]:


i_94.info()


# In[5]:


i_94['traffic_volume'].describe()


# In[6]:


import matplotlib.pyplot as plt
get_ipython().run_line_magic('matplotlib', 'inline')
i_94['traffic_volume'].plot.hist(bins=20)
plt.xlabel('Traffic volume')
plt.title('Traffic volume')
plt.show()


# In[7]:


i_94['date_time'] = pd.to_datetime(i_94['date_time'])


# In[8]:


i_94['date_time'].describe()


# ## Traffic volume: day vs night
# We devide the dataset into two parts:
# - Datetime data: from 7am to 7pm
# - Nightime data: from 7pm to 7 am    

# In[9]:


day = i_94.copy()[(i_94['date_time'].dt.hour >=7)&(i_94['date_time'].dt.hour <19)]
day.info()


# In[10]:


night = i_94.copy()[(i_94['date_time'].dt.hour >=19)|(i_94['date_time'].dt.hour <7)]
night.info()


# In[11]:


plt.figure(figsize=(12,4))

plt.subplot(1,2,1)
plt.hist(day['traffic_volume'])
plt.xlim(0,7500)
plt.ylim(0,8000)
plt.title('Traffic volume: Day')
plt.xlabel('Traffic volume')

plt.subplot(1,2,2)
plt.hist(night['traffic_volume'])
plt.xlim(0,7500)
plt.ylim(0,8000)
plt.title('Traffic volume: Night')
plt.xlabel('Traffic volume')
plt.show()


# In[12]:


day['traffic_volume'].describe()


# In[13]:


day['month']=day['date_time'].dt.month
by_month = day.groupby('month')['traffic_volume'].mean()
by_month.plot.line()
plt.show()


# The traffic looks less heavy during cold months (November - Jebruary) and more intense during warm months (March - October)

# In[14]:


day['year'] = day['date_time'].dt.year
only_july = day[day['month'] == 7].groupby('year')['traffic_volume'].mean()
only_july.plot.line()
plt.show()


# The reason of this exception in 2016 is the construction in I-94 with this article:
# https://www.dot.state.mn.us/I-94minneapolis-stpaul/

# In[15]:


day['dayofweek']=day['date_time'].dt.dayofweek
by_dayofweek = day.groupby('dayofweek')['traffic_volume'].mean()
by_dayofweek.plot.line()
plt.show()


# During business days, the traffic volume is about 5000. And during weekends, the traffic volume is below 4000

# In[16]:


day['hour'] = day['date_time'].dt.hour

business_day = day.copy()[day['dayofweek']<=4]
weekend = day.copy()[day['dayofweek']>4]

by_business_day = business_day.groupby('hour')['traffic_volume'].mean()
by_weekend = weekend.groupby('hour')['traffic_volume'].mean()

plt.figure(figsize = (12,4))
plt.subplot(1,2,1)
by_business_day.plot.line()
plt.xlim(6,20)
plt.ylim(1000,7000)
plt.title('Traffic volume by hour: Monday-Friday')

plt.subplot(1,2,2)
by_weekend.plot.line()
plt.xlim(6,20)
plt.ylim(1000,7000)
plt.title('Traffic volume by hour: weekend')

plt.show()


# The rush hour are round 7 and 16 - when most people travel from home to work and back. We can see volumes of over 6000 cars at rush hour

# In[17]:


day.corr()['traffic_volume']


# In[18]:


day.plot.scatter('traffic_volume','temp')
plt.ylim(220,320)
plt.show()


# We can conclude that the temperature doesn't look like a strong indicator of heavy traffic

# In[19]:


day.columns


# In[20]:


day['weather_main'].value_counts().head()


# In[21]:


day['weather_description'].value_counts().head()


# In[22]:


by_weather_main = day.groupby('weather_main')['traffic_volume'].mean()
by_weather_main.plot.barh()
plt.show()


# In[23]:


by_weather_description = day.groupby('weather_description')['traffic_volume'].mean()
by_weather_description.plot.barh(figsize = (6,10))
plt.show()


# The 3 weathers types where the traffic volume exceeds 5000 cars:
# - shower snow
# - light rain and snow
# - proximity thunderstorm with drizzle

# ### Conclusion:
#  - The traffic looks less heavy during cold months (November - Jebruary) and more intense during warm months (March - October)
#  - We can conclude that the temperature doesn't look like a strong indicator of heavy traffic
#  - Three weathers types where the traffic volume exceeds 5000 cars:
#     - shower snow
#     - light rain and snow
#     - proximity thunderstorm with drizzle
