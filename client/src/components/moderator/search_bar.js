import React, { Component } from "react"

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

  render() {
    return (
      <nav className="navbar justify-content-between py-4">
        <div className="container">
          <form className="form-inline" id="search-form" onSubmit={this.handleSubmit}>
            <input type="text" className="form-control" placeholder="Search..." onChange={this.onSearchTextChange.bind(this)}/>
            <button type="submit" className="btn btn-primary ml-2">Search</button>
          </form>
          <div>
            <a href="#">Export xls</a>
          </div>
        </div>
      </nav>
    )
  }
}

export default SearchBar