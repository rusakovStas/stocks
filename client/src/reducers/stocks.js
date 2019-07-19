import {
	GET_STOCKS,
	ADD_STOCK,
	DELETE_STOCK,
	GET_FAVORITE_STOCKS,
	GET_FAVORITE_STOCKS_SUMMARY
} from "../actions/types";

export default function stocks(
	state = {
		allStocks: [],
		favoriteStocks: [],
		summary: { value: null, allocations: [] }
	},
	action
) {
	switch (action.type) {
		case GET_STOCKS:
			return { ...state, allStocks: action.stocks };
		case GET_FAVORITE_STOCKS:
			return {
				...state,
				favoriteStocks: action.stocks
			};
		case GET_FAVORITE_STOCKS_SUMMARY:
			return { ...state, summary: action.summary };
		case ADD_STOCK:
			return {
				...state,
				favoriteStocks: state.favoriteStocks.concat(action.stock)
			};
		case DELETE_STOCK:
			return {
				...state,
				favoriteStocks: state.favoriteStocks.filter(
					stock => stock.stockId !== action.stock.stockId
				)
			};
		default:
			return state;
	}
}
