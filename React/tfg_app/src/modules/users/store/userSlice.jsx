import { createSlice } from "@reduxjs/toolkit";

const initialState = {

    name: "Alex",
    username: "",
    email: "",
    token: "",
    role: "",

}

export const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        // loginUser: (state, action) => {
        //     const {name, username, email, token, role } = action.payload;
        //     state.name = name;
        //     state.username = username;
        //     state.email = email;
        //     state.token = token;
        //     state.role = role;
        // },

        loginUser: (state) => {
                state.name = "Álex";
                state.username = "";
                state.email = "";
                state.token = "Álex";
                state.role = "";
            },

        logoutuser: (state) => {
            state.name = "";
            state.username = "";
            state.email = "";
            state.token = "";
            state.role = "";
        }

    }

});

export const {loginUser, logoutuser} = userSlice.actions
export default userSlice.reducer;