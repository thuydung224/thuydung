#!/usr/bin/env python
# coding: utf-8

# ## Introduction
# The aim of this project is to identify profitable Android (Google Play) and iOS (the App Store) mobile apps.
# 
# The apps in consideration are free to download and install, and the main source of the company's revenue consists of in-app ads. This means the revenue for any given app is mostly influenced by the number of its users - the more users that see and engage with the ads, the better. Hence it is necessary to analyze available data to understand what type of apps are likely to attract more users both on Google Play and the App Store.
# ## 1. Data Collection and Exploration
# As of [September 2018](https://www.statista.com/statistics/276623/number-of-apps-available-in-leading-app-stores/), there were approximately 2 million iOS apps available on the App Store, and 2.1 million Android apps on Google Play.
# 
# Collecting data for over 4 million apps requires a significant amount of time and money, so we'll try first to analyze a sample of the data instead, to see if we can find any relevant existing data at no cost. For this purpose, there are 2 data sets available in the form of CSV files:
# 
# - [Android apps data set](https://www.kaggle.com/lava18/google-play-store-apps) contains data about approximately 10,000 Android apps from Google Play; the data was collected in August 2018.
# - [IOS apps data set](https://www.kaggle.com/ramamet4/app-store-apple-data-set-10k-apps) contains data about approximately 7,000 iOS apps from the App Store; the data was collected in July 2017. 
# 
# #### I.  Opening the data sets

# In[1]:


import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np
pd.set_option('display.float_format', lambda x: '%.2f' % x)


# In[2]:


ios = pd.read_csv('AppleStore.csv')
ios.head(3)


# In[3]:


ios.info()


# In[4]:


def plot_correlations(df):
    # create a correlation matrix 
    df_corr = df.corr()
    
    # create a mask to avoid repeated values and make
    # the plot easier to read
    df_corr = df_corr.iloc[1:, :-1]
    mask = np.triu(np.ones_like(df_corr), k=1)
    
    # plot a heatmap of the values
    plt.figure(figsize=(20,14))
    ax = sns.heatmap(df_corr, vmin=-1, vmax=1, cbar=False,
                     cmap='RdBu', mask=mask, annot=True)
    
    # format the text in the plot to make it easier to read
    for text in ax.texts:
        t = float(text.get_text())
        if -0.05 < t < 0.01:
            text.set_text('')
        else:
            text.set_text(round(t, 2))
        text.set_fontsize('x-large')
    plt.xticks(rotation=90, size='x-large')
    plt.yticks(rotation=0, size='x-large')

    plt.show()


# In[5]:


plot_correlations(ios)


# In[6]:


ios.isnull().sum()


# In[7]:


android = pd.read_csv('googleplaystore.csv')
android.head(3)


# In[8]:


android.info()


# In[9]:


android.columns = android.columns.str.lower().str.strip().str.replace(' ','_')
android.columns


# In[10]:


android.isnull().sum()


# The Google Play  data set (Android apps) contains 10,841 apps and 13 columns. The most informative columns for us seem to be the following: `'App'`, `'Category'`, `'Rating'`, `'Reviews'`, `'Installs'`, `'Type'`, `'Price'`, `'Content Rating'` and `'Genres'`.
# 
# The App Store data set (iOS apps) contains 7,197 apps and 16 columns. The columns potentially useful for our data analysis might be the following: `'track_name'`, `'currency'`, `'price'`, `'rating_count_tot'`, `'rating_count_ver'`, `'user_rating'`, `'user_rating_ver'`, `'cont_rating'` and `'prime_genre'`.
# 
# For further details about both data sets and the meaning of each column, the corresponding data set documentation can be addressed: 
# [Android apps data set](https://www.kaggle.com/lava18/google-play-store-apps) and [iOS apps data set](https://www.kaggle.com/ramamet4/app-store-apple-data-set-10k-apps).
# #### II. Data Cleaning
# ##### II.1. Deleting Wrong Data
# For both data sets discussion sections are available here: [for Google Play](https://www.kaggle.com/lava18/google-play-store-apps/discussion) and [for the App Store](https://www.kaggle.com/ramamet4/app-store-apple-data-set-10k-apps/discussion). In the discussion section dedicated to **Google Play data set** in [one of the topics](https://www.kaggle.com/lava18/google-play-store-apps/discussion/66015) it was reported a wrong value for the row 10,472 (missing `'Rating'` and a column shift for next columns).

# In[11]:


android = android[(android['rating']<=5)|(android['rating'].isnull())]
android.shape


# ##### II.2. Deleting Duplicates
# Exploring the **Google Play data set**, it was discovered that some apps have duplicate entries. We need to remove the duplicate entries and keep only one entry per app.

# In[12]:


android.duplicated(subset=['app']).value_counts()


# In[13]:


android = android.drop_duplicates(['app'])
android.shape


# ##### II.3. Deleting Non-English Apps
# Since our company uses only English to develop its apps,
# we'd like to analyze only the apps that are directed toward an English-speaking audience.
# 
# Inspecting both data sets, it was detected that both have also apps with non-English names, that is containing symbols unusual for English texts (i.e. not English letters, digits 0-9, punctuation marks, and special symbols). These apps have to be removed.

# In[14]:


def english(string):
    non_ascii = 0
    for character in string:
        if ord(character) > 127:
            non_ascii += 1
    if non_ascii > 3:
        return False
    else:
        return True


# In[15]:


android=android[android['app'].apply(english)]
android.shape


# In[16]:


ios = ios[ios['track_name'].apply(english)]
ios.shape


# After filtering the data set with android apps counts 9,614 rows and the one with iOS apps 6,183 rows.
# ##### II.4. Deleting Non-Free Apps
# The company is specialized in building only free apps. Hence, before proceeding to the data analysis step, we have to remove all non-free apps from both data sets.

# In[17]:


ios['price']=ios['price'].astype('float')


# In[18]:


ios = ios[ios['price'] == 0.0]
ios.shape


# In[19]:


android['price'].unique()


# In[20]:


android['price']=android['price'].str.replace('$','').astype('float')
android = android[android['price'] == 0]
android.shape


# Now we have 8,862 android apps and 3,222 iOS apps for further data analysis.
# ##### II.5 Filling missing values

# In[21]:


def plot_null_matrix(df, figsize=(18,15)):
    # initiate the figure
    plt.figure(figsize=figsize)
    # create a boolean dataframe based on whether values are null
    df_null = df.isnull()
    # create a heatmap of the boolean dataframe
    sns.heatmap(df_null, cbar=False, yticklabels=False)
    plt.xticks(rotation=45, size='x-large')
    plt.show()


# In[22]:


plot_null_matrix(android)


# In[23]:


android['rating'].fillna((android['rating'].mean()), inplace=True)


# In[24]:


plot_null_matrix(android)


# ##### II.5 Cleaning 'installs' column

# In[25]:


android['installs'] = android['installs'].str.replace('+','').str.replace(',','').astype('float')


# In[26]:


android['installs'].value_counts().head()


# Now we have 8,864 android apps and 3,222 iOS apps for further data analysis.
# ## 3. Data Analysis
# As we mentioned in the introduction, our aim is to determine the kinds of apps that are likely to attract more users, because our revenue is highly influenced by the number of people using our apps.
# 
# To minimize risks and overhead, our validation strategy for an app idea is comprised of 3 steps:
# 
# - Build a minimal Android version of the app, and add it to Google Play.
# - If the app has a good response from users, we develop it further.
# - If the app is profitable after 6 months, we build an iOS version of the app and add it to the App Store.
# 
# Because our final goal is to add the app on both Google Play and the App Store, we need to find app profiles that are successful on both markets.
# ### 3.1. Finding The Most Common Genres
# Let's begin the analysis by getting a sense of what are the most common genres for each market. For Google Play data set the genres of the apps are described in the column `'Genres'` and `'Category'`, for the App Store data set - in the column `'prime_genre'`.
# 
# We'll build two functions we can use to analyze the frequency tables:
# 
# - To generate frequency tables that show percentages.
# - To display the percentages in a frequency table in a descending order.

# In[27]:


android_cat=android['category'].value_counts(normalize=True).head(10).sort_values()
android_cat


# In[28]:


import matplotlib.style as style
style.use('fivethirtyeight')
fig, ax = plt.subplots(figsize=(12, 6))
barlist = ax.barh(android_cat.index,android_cat,height=0.5,color='#4169e1')
barlist[-1].set_color('r') 
ax.grid(b=False)
ax.text(x=-0.11,y=11,s='The most common genre in Android English free apps',size = 24,weight='bold')
ax.set_xticks([0, 0.1, 0.18])
ax.set_xticklabels(['0%', '10%', '18%'])
plt.xlim(0,0.6)
ax.xaxis.tick_top()
ax.axvline(x=0.1, ymin=0.045, c='grey',
           alpha=0.5)
genres_names = android_cat.index
ax.set_yticklabels([])
for i, country in zip(range(11), genres_names):
    ax.text(x=-0.11, y=i-0.15, s=country)
print(plt.xlim())
print(plt.ylim())


# In[29]:


ios_gen=ios['prime_genre'].value_counts(normalize=True,ascending=False).head(10).sort_values()
ios_gen


# In[30]:


fig, ax = plt.subplots(figsize=(12, 6))
barlist = ax.barh(ios_gen.index,ios_gen,height=0.5,color='#4169e1')
barlist[-1].set_color('r')  
ax.grid(b=False)
ax.text(x=-0.11,y=11,s='The most common genre in IOS English free apps',size = 24,weight='bold')
ax.set_xticks([0, 0.29, 0.58])
ax.set_xticklabels(['0%', '29%', '58%'])
ax.xaxis.tick_top()
ax.axvline(x=0.29, ymin=0.045, c='grey',
           alpha=0.5)
genres_names = ios_gen.index
ax.set_yticklabels([])
for i, country in zip(range(11), genres_names):
    ax.text(x=-0.11, y=i-0.15, s=country)
barlist[-1].set_color('r')    
print(plt.xlim())
print(plt.ylim())


# In[31]:


android.groupby('category')['installs'].mean().sort_values(ascending=False).head()


# In[32]:


android[android['category']=='COMMUNICATION'].head()


# In[33]:


android_genre = android.groupby('category')['installs'].mean().sort_values(ascending=False).head(10).sort_values()
android_genre


# In[34]:


fig, ax = plt.subplots(figsize=(12, 6))
barlist = ax.barh(android_genre.index,android_genre,height=0.5,color='#4169e1')
barlist[-1].set_color('r')
barlist[-2].set_color('r')  
ax.grid(b=False)
ax.text(x=-9500000,y=11,s='The Most Popular Genres in Android English free apps',size = 24,weight='bold')
ax.set_xticks([0, 19138925, 38378925])
ax.set_xticklabels(['0M', '19M', '38M'])
ax.xaxis.tick_top()
ax.axvline(x=0.29, ymin=0.045, c='grey',
           alpha=0.5)
genres_names = android_genre.index
ax.set_yticklabels([])
for i, country in zip(range(11), genres_names):
    ax.text(x=-9500000, y=i-0.15, s=country)
print(plt.xlim())
print(plt.ylim())


# In[35]:


ios.groupby('prime_genre')['rating_count_tot'].mean().sort_values(ascending=False).head()


# In[36]:


ios[ios['prime_genre']=='Navigation']


# In[37]:


ios_genre = ios.groupby('prime_genre')['rating_count_tot'].mean().sort_values(ascending=False).head(10).sort_values()
ios_genre


# In[38]:


fig, ax = plt.subplots(figsize=(12, 6))
barlist = ax.barh(ios_genre.index,ios_genre,height=0.5,color='#4169e1')
barlist[-1].set_color('r')
barlist[-2].set_color('r')  
ax.grid(b=False)
ax.text(x=-11500,y=11,s='The Most Popular Genres in IOS English free apps',size = 24,weight='bold')
ax.set_xticks([0, 43050, 86100])
ax.set_xticklabels(['0K', '43K', '86K'])
ax.xaxis.tick_top()
ax.axvline(x=0.29, ymin=0.045, c='grey',
           alpha=0.5)
genres_names = ios_genre.index
ax.set_yticklabels([])
for i, country in zip(range(11), genres_names):
    ax.text(x=-15500, y=i-0.15, s=country)

