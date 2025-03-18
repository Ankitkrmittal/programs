# ===========================
# BACKEND (Data Processing)
# ===========================

# Import necessary libraries


import streamlit as st
import pandas as pd
import re
import google.generativeai as genai
import os
from dotenv import load_dotenv
from streamlit_chat import message

# Load API Key
load_dotenv()
genai.configure(api_key=os.getenv("GEMINI_API_KEY"))


# Function to load and process venue data
@st.cache_data
def load_data():
    """Loads and processes venue data from CSV."""
    venues = pd.read_csv("venues.csv")

    # Ensure correct column names (remove unwanted spaces)
    venues.columns = venues.columns.str.strip()

    # Convert 'Capacity' column to integer (handling ranges like "100-500")
    def parse_capacity(cap):
        if isinstance(cap, str) and re.match(r"^\d+(-\d+)?$", cap):
            return int(cap.split("-")[-1])  # Get highest value in range
        try:
            return int(cap)
        except ValueError:
            return None  # Handle invalid values

    venues["Capacity"] = venues["Capacity"].apply(parse_capacity)
    venues = venues.dropna(subset=["Capacity"])  # Remove empty values
    venues["Capacity"] = venues["Capacity"].astype(int)

    # Convert 'Budget' column to integer (removing ₹ symbol & commas)
    venues["Budget"] = venues["Budget"].replace({"₹": "", ",": ""}, regex=True).astype(int)

    return venues

# Load venue data (Cached for performance)
venues = load_data()

# ===========================
# FRONTEND (User Interface)
# ===========================

# Title and description
st.title("🎪 AI-Powered Event Planner")
st.write("Plan your event step-by-step with smart venue and budget recommendations!")

# ---------------------------------------
# User Inputs (Event Planning Filters)
# ---------------------------------------

# Step 1: Select Event Type
event_type = st.selectbox("🎭 Select Event Type:", 
    ["Wedding", "Corporate Event", "Birthday", "Conference", "Concert", "Exhibition"])

# Step 2: Select Location
locations = venues["Location"].unique()
selected_location = st.selectbox("📍 Select Location:", ["Any"] + list(locations))

# Step 3: Select Number of Attendees
attendees = st.slider("👥 Number of attendees:", min_value=10, max_value=5000, step=10, value=100)

# Step 4: Select Budget
budget = st.slider("💰 Select Your Budget (₹):", min_value=5000, max_value=200000, step=5000, value=50000)

# Step 5: Select Venue Type
venue_type = st.radio("🏢 Select Venue Type:", ["Indoor", "Outdoor", "Any"])

# Step 6: Select Required Amenities
amenities_list = ["Mic", "Projector", "Sound System", "Lighting", "WiFi", "Seating", "Catering", "Valet Parking"]
selected_amenities = st.multiselect("🎤 Select Required Amenities:", amenities_list)


# Add AI Recommendation Function
def generate_ai_recommendation(event_summary, venues):
    """Uses Google Gemini AI to suggest the best venues."""
    venue_details = "\n".join([
        f"- {row['Venue Name']} in {row['Location']} (Capacity: {row['Capacity']}, Budget: ₹{row['Budget']})"
        for _, row in venues.iterrows()
    ])
    
    prompt = f"""
    I am planning an event with the following details:
    {event_summary}

    Here are some venue options:
    {venue_details}

    Based on these details, suggest the best venue(s) and explain why.
    """
    
    try:
        model = genai.GenerativeModel("gemini-1.5-pro-latest")
        response = model.generate_content(prompt)
        return response.text  # ✅ Correct way to get Gemini output
    except Exception as e:
        return f"AI recommendation failed: {str(e)}"


# ===========================
# BACKEND (Filtering Data)
# ===========================

# Apply venue filters based on user selections
filtered_venues = venues[
    (venues["Capacity"] >= attendees) & 
    (venues["Budget"] <= budget) & 
    (venues["Event Type"].str.contains(event_type, case=False, na=False))
]

if venue_type != "Any":
    filtered_venues = filtered_venues[filtered_venues["Type"].str.lower() == venue_type.lower()]

if selected_location != "Any":
    filtered_venues = filtered_venues[filtered_venues["Location"] == selected_location]

if selected_amenities:
    filtered_venues = filtered_venues[
    filtered_venues["Available Equipments"].fillna("").apply(lambda x: all(item in x for item in selected_amenities))
]


# ===========================
# FRONTEND (Display Results)
# ===========================

# Step 7: Show Recommended Venues
st.subheader("🏠 Recommended Venues:")


# Step 8: UI to Show AI-Powered Recommendations
if not filtered_venues.empty:
    st.success(f"✅ Found {len(filtered_venues)} matching venues!")
    st.dataframe(filtered_venues[["Venue Name", "Location", "Capacity", "Type", "Budget", "Event Type"]])

    # 📥 Download Venue List
    csv = filtered_venues.to_csv(index=False).encode('utf-8')
    st.download_button("📥 Download Venue List", csv, "recommended_venues.csv", "text/csv")
else:
    st.warning("⚠ No venues match your criteria. Try adjusting your filters.")



# Generate AI-powered recommendations
event_summary_text = f"""
Event Type: {event_type}
Location: {selected_location}
Number of Attendees: {attendees}
Budget: ₹{budget}
Venue Type: {venue_type}
Selected Amenities: {', '.join(selected_amenities) if selected_amenities else 'None'}
    """

    
ai_suggestions = generate_ai_recommendation(event_summary_text, filtered_venues)

st.subheader("🤖 AI-Powered Venue Suggestions:")
st.write(ai_suggestions)




# ===========================
# AI Chatbot - Event Planning Assistant
# ===========================

from streamlit_chat import message
st.subheader("💬 Chat with Event Planner AI")

# Initialize chat history if not exists
if "chat_history" not in st.session_state:
    st.session_state.chat_history = [
        {"role": "assistant", "content": "Hello! How can I help you with event planning today?"}
    ]

# Display chat history with unique keys
for idx, chat in enumerate(st.session_state.chat_history):
    message(chat["content"], is_user=(chat["role"] == "user"), key=f"chat_{idx}")


# User input for chatbot
user_query = st.chat_input("Ask me anything about event planning:")

if user_query and st.session_state.get("last_input") != user_query:
    st.session_state.last_input = user_query  # Store last input to avoid re-processing
    # Append user input to chat history
    st.session_state.chat_history.append({"role": "user", "content": user_query})

    # Call AI model for response
    try:
        model = genai.GenerativeModel("gemini-1.5-pro-latest")
        response = model.generate_content(user_query)
        bot_reply = response.text if response else "I'm not sure, can you rephrase?"
    except Exception as e:
        bot_reply = f"AI failed: {str(e)}"

    # Display AI response
    message(bot_reply, is_user=False)

    # Append AI response to chat history
    st.session_state.chat_history.append({"role": "assistant", "content": bot_reply})
    st.rerun()  # Force UI refresh to keep input box visible
1



# Footer
st.write("---")