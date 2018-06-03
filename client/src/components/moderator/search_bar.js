import React, { Component } from "react"
import {message} from "../../locale/message"

class SearchBar extends Component {
  constructor(props) {
    super(props)

    this.state = {
      search: ''
    }
  }

  onSearchTextChange = (event) => {
    this.setState({ search: event.target.value })
  }

  handleSubmit = (event) => {
    event.preventDefault()
    this.props.onSearch(this.state.search)
  }

  exportXls = (event) => {
    event.preventDefault()
    this.props.onExportXls()
  }

  render() {
    const {lang, exportXlsLoading} = this.props

    return (
      <nav className="navbar justify-content-between py-4">
        <div className="container">
          <form className="form-inline" id="search-form" onSubmit={this.handleSubmit}>
            <input type="text" className="form-control" placeholder="Search..." onChange={this.onSearchTextChange.bind(this)}/>
            <button type="submit" className="btn btn-primary ml-2">{message.search[lang]}</button>
          </form>
          <div>
            <a href="#" onClick={this.exportXls.bind(this)}>{message.exportXls[lang]}</a>
            {exportXlsLoading && <span className="spinner ml-2"><i className="fa fa-spinner fa-spin fa-1x" /></span>}
          </div>
        </div>
      </nav>
    )
  }
}

export default SearchBar