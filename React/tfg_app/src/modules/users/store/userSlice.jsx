import { createSlice } from "@reduxjs/toolkit";

const initialState = {

    name: "Álex",
    username: "",
    email: "",
    token: "A",
    role: "",

}

export const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        loginUser: (state, action) => {
            const {name, username, email, token, role } = action.payload;
            state.name = name;
            state.username = username;
            state.email = email;
            state.token = token;
            state.role = role;
        }

    }

});

export const {loginUser} = userSlice.actions
export default userSlice.reducer;