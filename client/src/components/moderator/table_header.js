import React, { Component } from "react"

import {message} from "../../locale/message"

class TableHeader extends Component {
  render() {
    const {lang} = this.props

    return (
      <section className="table-header py-4 mb-4">
        <div className="container">
          <div className="row text-center">
            <div className="col-lg-1 mb-1 mb-lg-0">
              <p>#</p>
            </div>
            <div className="col-lg-2 mb-1 mb-lg-0">
              <p className="text-center">{message.first_name[lang]}</p>
            </div>
            <div className="col-lg-2 mb-1 mb-lg-0">
              <p className="text-center">{message.patronymic[lang]}</p>
            </div>
            <div className="col-lg-2 mb-1 mb-lg-0">
              <p className="text-center">{message.last_name[lang]}</p>
            </div>
            <div className="col-lg-2 mb-1 mb-lg-0">
              <p className="text-center">{message.iin[lang]}</p>
            </div>
            <div className="col-lg-1 mb-1 mb-lg-0">
              <p className="text-center">{message.gender[lang]}</p>
            </div>
            <div className="col-lg-2 mb-1 mb-lg-0">
              <p className="text-center">{message.status[lang]}</p>
            </div>
          </div>
        </div>
      </section>
    )
  }
}

export default TableHeader