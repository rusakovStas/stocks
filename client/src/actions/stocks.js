import {
	GET_STOCKS,
	ADD_STOCK,
	DELETE_STOCK,
	GET_FAVORITE_STOCKS,
	GET_FAVORITE_STOCKS_SUMMARY
} from "./types";
import api from "../api/api";

export const getStocksFromRs = stocks => ({
	type: GET_STOCKS,
	stocks
});

export const getFavoriteStocksFromRs = stocks => ({
	type: GET_FAVORITE_STOCKS,
	stocks
});

export const getSummaryForStocks = summary => ({
	type: GET_FAVORITE_STOCKS_SUMMARY,
	summary
});

export const addStock = stock => ({
	type: ADD_STOCK,
	stock
});

export const del = stock => ({
	type: DELETE_STOCK,
	stock
});

export const getStocks = () => dispatch => {
	api.stocks.getAllStocks().then(stocks => {
		dispatch(getStocksFromRs(stocks));
	});
};

export const getMyFavotiteStocks = () => dispatch => {
	api.stocks.getMyStocks().then(stocks => {
		dispatch(getFavoriteStocksFromRs(stocks));
	});
};

export const getSummary = () => dispatch => {
	api.stocks.getSummaryForFavorite().then(summary => {
		dispatch(getSummaryForStocks(summary));
	});
};

export const addStockToChosen = data => dispatch =>
	api.stocks.addStock(data).then(chosenStock => {
		dispatch(addStock(chosenStock));
	});

export const deleteStockFromChosen = stock => dispatch =>
	api.stocks.deleteStock(stock).then(() => {
		dispatch(del(stock));
	});
