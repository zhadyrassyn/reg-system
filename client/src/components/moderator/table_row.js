import React, { Component } from "react"

class TableRow extends Component {
  render() {
    const { student, index } = this.props
    const { firstName, middleName, lastName, birthDate, school, city, email } = student

    return (
      <li className="py-4">
        <div className="row justify-content-lg-around">
          <div className="col-lg-1 mb-1 mb-lg-0">
            <p>{ index }</p>
          </div>
          <div className="col-lg-1 mb-1 mb-lg-0">
            <p>{ firstName }</p>
          </div>
          <div className="col-lg-1 mb-1 mb-lg-0">
            <p>{ middleName }</p>
          </div>
          <div className="col-lg-1 mb-1 mb-lg-0">
            <p>{ lastName }</p>
          </div>
          <div className="col-lg-1 mb-1 mb-lg-0">
            <p>{ birthDate }</p>
          </div>
          <div className="col-lg-1 mb-1 mb-lg-0">
            <p>{ city }</p>
          </div>
          <div className="col-lg-1 mb-1 mb-lg-0">
            <p>{ school }</p>
          </div>
        </div>
      </li>
    )
  }
}

export default TableRow