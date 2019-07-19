import React from "react";
import PropTypes from "prop-types";
import { Typeahead } from "react-bootstrap-typeahead";
import {
	Container,
	Card,
	Button,
	Input,
	Row,
	Col,
	CardText,
	CardTitle,
	Modal,
	ModalBody,
	ModalHeader,
	Table
} from "reactstrap";
import NumberFormat from "react-number-format";

class StocksForm extends React.Component {
	state = {
		chosen: [],
		data: {
			symbol: "",
			iexId: "",
			name: "",
			volume: ""
		},
		open: false,
		loading: false
	};

	show = val => {
		if (val) {
			this.props.getSummary();
		}
		this.setState({ open: val });
	};

	handleOptionSelected = option => {
		if (option.length >= 1) {
			this.setState({
				data: {
					...this.state.data,
					symbol: option[0].symbol,
					name: option[0].name
				},
				chosen: option
			});
		}
	};

	addToFavorite = () => {
		this.props.add(this.state.data);
	};

	onChangeValue = values => {
		this.setState({ data: { ...this.state.data, volume: values.value } });
	};

	render() {
		return (
			<div>
				<Container>
					<Card
						body
						id={1}
						outline
						color="white"
						className="text-center shadow"
					>
						<Typeahead
							id={2}
							renderMenuItemChildren={option => (
								<div>
									{option.name}
									<div>
										<small>
											symbol:
											{option.symbol}
										</small>
									</div>
								</div>
							)}
							labelKey={option =>
								`${option.symbol} ${option.name}`
							}
							options={this.props.suggestions}
							placeholder="Type stocks tiket..."
							className="pb-2"
							onChange={this.handleOptionSelected}
							selected={this.state.chosen}
						/>
						<NumberFormat
							className="mb-2"
							customInput={Input}
							decimalScale={0}
							allowNegative={false}
							thousandSeparator=" "
							placeholder="Type value..."
							error="wrong"
							success="right"
							id="value"
							name="value"
							value={this.state.data.value}
							onValueChange={this.onChangeValue}
							disabled={this.state.loading}
						/>
						<Button color="primary" onClick={this.addToFavorite}>
							Add to favorite
						</Button>
						{this.props.favoriteStocks.length > 0 && (
							<Button
								className="m-3"
								color="success"
								onClick={() => this.show(true)}
							>
								Get Summary
							</Button>
						)}
					</Card>
					<Row>
						{this.props.favoriteStocks.map(st => (
							<Col md="4" className="padding-10">
								<Card
									key={st.stockId}
									body
									outline
									color="white"
									className="text-center shadow"
								>
									<CardTitle tag="h2">{st.name}</CardTitle>
									<CardText>Price: {st.price}</CardText>
									<CardText>Sector: {st.sector}</CardText>
									<CardText>Volume: {st.volume}</CardText>
									<Button
										color="danger"
										onClick={() => this.props.delete(st)}
									>
										Delete from favorite{" "}
									</Button>
								</Card>
							</Col>
						))}
					</Row>
				</Container>

				<Modal
					isOpen={this.state.open}
					toggle={() => this.show(!this.state.open)}
					centered
				>
					<ModalBody>
						<ModalHeader toggle={() => this.show(!this.state.open)}>
							Summary
						</ModalHeader>
						<div className="p-3">
							Summ value: <b>{this.props.summary.value}</b>
						</div>
						<Table responsive>
							<thead>
								<tr>
									<th>#</th>
									<th>Sector</th>
									<th>[assetValue]</th>
									<th>[Proportion]</th>
								</tr>
							</thead>
							<tbody>
								{this.props.summary.allocations.map(
									(al, index) => (
										<tr>
											<th scope="row">{index + 1}</th>
											<th>{al.sector}</th>
											<th>{al.assetValue}</th>
											<th>{al.proportion}</th>
										</tr>
									)
								)}
							</tbody>
						</Table>
					</ModalBody>
				</Modal>
			</div>
		);
	}
}

StocksForm.propTypes = {
	delete: PropTypes.func.isRequired,
	add: PropTypes.func.isRequired,
	getSummary: PropTypes.func.isRequired,
	suggestions: PropTypes.arrayOf(
		PropTypes.shape({
			iexId: PropTypes.string.isRequired,
			symbol: PropTypes.string.isRequired,
			name: PropTypes.string.isRequired
		})
	).isRequired,
	summary: PropTypes.shape({
		value: PropTypes.number.isRequired,
		allocations: PropTypes.arrayOf(PropTypes.object).isRequired
	}).isRequired,
	favoriteStocks: PropTypes.arrayOf(
		PropTypes.shape({
			iexId: PropTypes.string.isRequired,
			symbol: PropTypes.string.isRequired,
			name: PropTypes.string.isRequired,
			sector: PropTypes.string.isRequired,
			price: PropTypes.number.isRequired,
			volume: PropTypes.number.isRequired
		})
	).isRequired
};

export default StocksForm;
