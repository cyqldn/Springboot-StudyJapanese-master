package com.chun.myspringboot.service.Impl;

import com.chun.myspringboot.mapper.SearchMapper;
import com.chun.myspringboot.mapper.WordMapper;
import com.chun.myspringboot.pojo.Word;
import com.chun.myspringboot.service.WordService;
import com.chun.myspringboot.util.MyException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class WordServiceImpl implements WordService {
    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private SearchMapper searchMapper;

    @Override
    public int addWord(Word word) {
        return wordMapper.addWord(word);
    }

    @Override
    public int deleteWord(Integer wordId) {
        return wordMapper.deleteWord(wordId);
    }

    @Override
    public int updateWord(Word word) {
        return wordMapper.updateWord(word);
    }

    @Override
    public int updateWordStudy1(Integer wordId) {
        return wordMapper.updateWordStudy1(wordId);
    }

    @Override
    public int updateWordStudy0(Integer wordId) {
        return wordMapper.updateWordStudy0(wordId);
    }

    @Override
    public int updateWordRemember1(Integer wordId) {
        return wordMapper.updateWordRemember1(wordId);
    }

    @Override
    public int updateWordRemember0(Integer wordId) {
        return wordMapper.updateWordRemember0(wordId);
    }




    @Override
    public int updateWordCollection1(Integer wordId) {
        return wordMapper.updateWordCollection1(wordId);
    }

    @Override
    public int updateWordCollection0(Integer wordId) {
        return wordMapper.updateWordCollection0(wordId);
    }

    @Override
    public List<Word> queryAllWord() {
        return wordMapper.queryAllWord();
    }

    @Override
    public Word queryWordById(Integer wordId) {
        return wordMapper.queryWordById(wordId);
    }

    /**
     * 分页查询收藏内容
     *
     */

    @Override
    public PageInfo<Word> queryAllCollectionWordByPage(int pageNum, int pageSize) {
        //设置分页信息，分别是当前页数和每页显示的总记录数
        PageHelper.startPage(pageNum, pageSize);
        //查询所有收藏的单词
        List<Word> word = wordMapper.queryAllWordCollection();
        //返回的是一个PageInfo,包含了分页的所有信息
        PageInfo<Word> pageInfo = new PageInfo<Word>(word);
        return pageInfo;

    }





    @Override
    public Word queryWordStudy0ByGrade(Integer grade) {
        return wordMapper.queryWordStudy0ByGrade(grade);
    }

    @Override
    public int updateWordStudyByGrade(Integer grade) {
        return wordMapper.updateWordStudyByGrade(grade);
    }


    @Override
    public List<Word> queryAllWordCollection() {
        return wordMapper.queryAllWordCollection();
    }

    @Override
    public List<Word> queryWordCollectionByGrade(Integer grade) {
        return wordMapper.queryWordCollectionByGrade(grade);
    }




    @Override
    public List<Word> queryAllRemember() {
        return queryAllRemember();
    }

    @Override
    public List<Word> queryAllWordRememberByGrade(Integer grade) {
        return wordMapper.queryAllWordRememberByGrade(grade);
    }


    @Override
    public List<Word> queryAllUnremembered() {
        return wordMapper.queryAllUnremembered();
    }

    @Override
    public List<Word> queryAllWordUnrememberedByGrade(Integer grade) {
        return wordMapper.queryAllWordUnrememberedByGrade(grade);
    }

    @Override
    public int queryAllWordNumberByGrade(Integer grade) {
        return wordMapper.queryAllWordNumberByGrade(grade);
    }

    @Override
    public int queryRememberNumberByGrade(Integer grade) {
        return wordMapper.queryRememberNumberByGrade(grade);
    }

    @Override
    public int queryStudyNumberByGrade(Integer grade) {
        return wordMapper.queryStudyNumberByGrade(grade);
    }

    @Override
    public List<Word> findByNameLike(String wordName) {
        return wordMapper.findByNameLike(wordName);
    }

    @Override
    public List<Word> findByNameLikeC(String wordName) {
        return wordMapper.findByNameLikeC(wordName);
    }


    @Override
    public int addWordFile(MultipartFile file) throws Exception {

        int result = 0;
        List<Word> wordList = new ArrayList<>();
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        System.out.println("suffix=" + suffix);
        InputStream ins = file.getInputStream();
        Workbook wb = null;
        if (suffix.equals("xlsx")) {
            wb = new XSSFWorkbook(ins);
        } else {
            wb = new HSSFWorkbook(ins);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (null != sheet) {
            for (int line = 2; line <= sheet.getLastRowNum(); line++) {
                Word word = new Word();
                Row row = sheet.getRow(line);
                if (null == row) {
                    continue;
                }
                /**
                 * 判断单元格类型是否为文本类型
                 */
                if (1 != row.getCell(0).getCellType()) {
                    throw new MyException("单元格类型不是文本类型！");
                }
                /**
                 * 获取第一个单元格的内容
                 */
                String wordName = row.getCell(0).getStringCellValue();
                String audio = row.getCell(1).getStringCellValue();
                String explanation = row.getCell(2).getStringCellValue();
                String example=row.getCell(3).getStringCellValue();
                //改变excel里的数据类型（excel中数字转为String）
               // row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                //转换为number类型
                row.getCell(4).setCellType(Cell.CELL_TYPE_NUMERIC);
                /**
                 * 获取第二个单元格的内容
                 */

                //强行得到int类型
                int grade = (int) row.getCell(4).getNumericCellValue();

                word.setWordName(wordName);
                word.setAudio(audio);
                word.setGrade(grade);
                word.setExplanation(explanation);
                word.setExample(example);
                wordList.add(word);
            }
            for (Word userInfo : wordList) {
                String name = userInfo.getWordName();
                int count = wordMapper.selectWordFile(name);
                if (0 == count) {
                    result = wordMapper.addWord(userInfo);
                } else {
                    result = wordMapper.updateWordFile(userInfo);
                }

            }
        }

        return result;
    }
}
