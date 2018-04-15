import React, { Component } from "react"
import PaginationItem from "./pagination_item"

class Pagination extends Component {
  constructor(props) {
    super(props)

    this.handleChangePage = this.handleChangePage.bind(this)
  }

  handleChangePage(pageNum) {
    this.props.handlePageChangeClick(pageNum)
  }

  render() {
    const { currentPage, perPage, total } = this.props
    const totalPages = Math.ceil(22/perPage)

    let pageItems = []
    for(let i = 1; i <= totalPages; i++) {
      if(i === 1 || i === totalPages ||
        (i <= currentPage + 2 && i >= currentPage) || (i >= currentPage - 2 && i <= currentPage)
      ) {
        pageItems.push(<PaginationItem key={i} value={i} pageChangeClicked={this.handleChangePage} currentPage={currentPage}/>)
      }

    }

    return (
      <footer className="container">
        <nav aria-label="Page navigation example" className="nav justify-content-end">
          <ul className="pagination">
            {pageItems}
          </ul>
        </nav>
      </footer>
    )
  }
}

export default Pagination