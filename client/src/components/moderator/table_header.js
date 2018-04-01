import React, { Component } from "react"

class TableHeader extends Component {
  render() {
    return (
      <section className="table-header py-4 mb-4">
        <div className="container">
          <div className="row text-center">
            <div className="col-lg-1 mb-1 mb-lg-0">
              <p>#</p>
            </div>
            <div className="col-lg-2 mb-1 mb-lg-0">
              <p>First name</p>
            </div>
            <div className="col-lg-2 mb-1 mb-lg-0">
              <p>Middle name</p>
            </div>
            <div className="col-lg-2 mb-1 mb-lg-0">
              <p>Last name</p>
            </div>
            <div className="col-lg-1 mb-1 mb-lg-0">
              <p>Date of birth</p>
            </div>
            <div className="col-lg-2 mb-1 mb-lg-0">
              <p>City</p>
            </div>
            <div className="col-lg-2 mb-1 mb-lg-0">
              <p>School</p>
            </div>
          </div>
        </div>
      </section>
    )
  }
}

export default TableHeader