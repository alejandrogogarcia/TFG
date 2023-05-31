import { createSlice } from "@reduxjs/toolkit";

const initialState = {

    name: "",
    username: "",
    email: "",
    token: "",


}

export const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        loginUser: (state, action) => {
            const {name, username, email, token } = action.payload;
            state.name = name;
            state.username = username;
            state.email = email;
            state.token = token;
        }

    }

})