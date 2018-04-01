import React, { Component } from "react"

class SearchBar extends Component {
  render() {
    return (
      <nav className="navbar justify-content-between py-4">
        <div className="container">
          <form className="form-inline" id="search-form">
            <input type="text" className="form-control" placeholder="Search..."/>
            <button type="button" className="btn btn-primary ml-2">Search</button>
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