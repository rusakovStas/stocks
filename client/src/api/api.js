import axios from "axios";
import BASE_URL from "./constants";

export default {
	user: {
		login: cred => {
			const config = {
				headers: { Authorization: "" }
			};
			return axios
				.post(
					`http://${BASE_URL}/authenticate?username=${
						cred.email
					}&password=${cred.password}`,
					"",
					config
				)
				.then(res => res.headers.authorization);
		}
	},
	admin: {
		getAllUsers: () =>
			axios.get(`http://${BASE_URL}/users/all`).then(res => res.data),
		addUser: user =>
			axios
				.post(`http://${BASE_URL}/users`, user)
				.then(response => response.data),
		deleteUser: user =>
			axios
				.delete(`http://${BASE_URL}/users?username=${user.username}`)
				.then(response => response.data)
	},
	stocks: {
		getAllStocks: () =>
			axios.get(`http://${BASE_URL}/stocks`).then(res => res.data),
		getMyStocks: () =>
			axios.get(`http://${BASE_URL}/stocks/my`).then(res => res.data),
		getSummaryForFavorite: () =>
			axios
				.get(`http://${BASE_URL}/stocks/summary`)
				.then(res => res.data),
		addStock: stock =>
			axios
				.post(`http://${BASE_URL}/stocks`, stock)
				.then(response => response.data),
		deleteStock: stock =>
			axios
				.delete(`http://${BASE_URL}/stocks?id=${stock.stockId}`)
				.then(response => response.data)
	}
};
