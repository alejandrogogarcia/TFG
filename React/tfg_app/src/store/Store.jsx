import { configureStore } from "@reduxjs/toolkit";
import userReducer from "./../modules/users/store/userSlice"

export const store = configureStore({

    reducer: {
        user: userReducer,
    }

})