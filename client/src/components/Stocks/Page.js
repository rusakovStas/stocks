import React from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import StocksForm from "./Form";
import {
	getStocks,
	addStockToChosen,
	deleteStockFromChosen,
	getMyFavotiteStocks,
	getSummary
} from "../../actions/stocks";

class StocksPage extends React.Component {
	state = {
		interval: {}
	};

	componentDidMount() {
		this.props.getStocks();
		this.props.getMyFavotiteStocks();
		this.setState({
			interval: setInterval(
				() => this.props.getMyFavotiteStocks(),
				1000 * 60 * 15
			)
		});
	}

	componentWillUnmount() {
		clearInterval(this.state.interval);
	}

	render() {
		return (
			<StocksForm
				suggestions={this.props.allStocks}
				getSummary={this.props.getSummary}
				summary={this.props.summary}
				favoriteStocks={this.props.favoriteStocks}
				add={this.props.addStockToChosen}
				delete={this.props.deleteStockFromChosen}
			/>
		);
	}
}

StocksPage.propTypes = {
	allStocks: PropTypes.arrayOf(PropTypes.object).isRequired,
	getMyFavotiteStocks: PropTypes.func.isRequired,
	getSummary: PropTypes.func.isRequired,
	summary: PropTypes.shape({
		value: PropTypes.number.isRequired,
		allocations: PropTypes.arrayOf(PropTypes.object).isRequired
	}).isRequired,
	favoriteStocks: PropTypes.arrayOf(PropTypes.object).isRequired,
	getStocks: PropTypes.func.isRequired,
	addStockToChosen: PropTypes.func.isRequired,
	deleteStockFromChosen: PropTypes.func.isRequired
};

function mapStateToProps(state) {
	return {
		allStocks: state.stocks.allStocks,
		favoriteStocks: state.stocks.favoriteStocks,
		summary: state.stocks.summary
	};
}

export default connect(
	mapStateToProps,
	{
		getStocks,
		addStockToChosen,
		deleteStockFromChosen,
		getMyFavotiteStocks,
		getSummary
	}
)(StocksPage);
