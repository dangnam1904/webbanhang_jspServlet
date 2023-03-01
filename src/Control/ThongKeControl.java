package Control;

import java.util.ArrayList;

import Model.dao_Products;
import Model.dao_ThongKe;
import Objects.ThongKes;

public class ThongKeControl {
	dao_ThongKe dao = new dao_ThongKe();

	// Lấy toàn bộ sản phẩm
	public ArrayList<ThongKes> getList(String tungay, String denngay) {

		String sql =" SELECT `hoadon`.`id`, `hoten`, `sonha`,`diachi`, `dienthoai`, `email`, \r\n"
				+ " ROUND(SUM(`chitiethoadon`.`soluong` * `sanpham`.`giagoc` - `chitiethoadon`.`soluong` *\r\n"
				+ " `sanpham`.`giagoc` * `sanpham`.`khuyenmai`/100), -3) AS tongtien, `ngaydat`\r\n"
				+ " FROM `sanpham`, `hoadon`, `chitiethoadon` WHERE `sanpham`.`id` = `chitiethoadon`.`id_sanpham` \r\n"
				+ " AND `chitiethoadon`.`id_hoadon` = `hoadon`.`id` AND `trangthai` = 2 AND `ngaydat` BETWEEN '"+tungay+"' \r\n"
				+ " AND '"+denngay+"' GROUP BY `ngaydat`,`hoadon`.`id`, `hoten`, `sonha`,`diachi`, `dienthoai`, `email` ORDER BY `ngaydat` ";
		return dao.SelectDB(sql);
	}
}
