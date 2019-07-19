import { combineReducers } from "redux";
import user from "./user";
import users from "./users";
import stocks from "./stocks";

export default combineReducers({
	user,
	users,
	stocks
});
