import React, { Component } from "react"
import TableRow from "./table_row"
import _ from "lodash"

class TableBody extends Component {

  renderList = (students, startCounter, openModal, lang) => {
    return _.map(students, student => (
      <TableRow
         student={ student }
         index={ ++startCounter }
         key={ student.id }
         openModal={openModal}
         lang={lang}
      />
    ))
  }


  render() {
    const { students, startCounter, openModal, lang } = this.props

    return (
      <section className="table-body">
        <div className="container">
          <ul className="list-unstyled" id="table-body">
            {this.renderList(students, startCounter, openModal, lang)}
          </ul>
        </div>
      </section>
    )
  }
}

export default TableBody