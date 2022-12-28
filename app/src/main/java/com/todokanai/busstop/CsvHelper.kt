package com.todokanai.busstop

import com.opencsv.CSVReader
import com.opencsv.CSVWriter
import com.todokanai.busstop.application.MyApplication
import com.todokanai.busstop.myobjects.MyObjects.csvPath
import com.todokanai.busstop.repository.StationRepository
import com.todokanai.busstop.room.MyDatabase
import com.todokanai.busstop.room.Station
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

class CsvHelper(private val filePath: String) {

    private val stationRepository = StationRepository(MyDatabase.getInstance(MyApplication.appContext).stationDao())

    fun writeAllData(fileName: String, dataList: ArrayList<Array<String>>) {
        try {
            FileWriter(File("$filePath/$fileName")).use { fw ->
                // writeAll()을 이용한 리스트 데이터 등록
                CSVWriter(fw).use {
                    it.writeAll(dataList)
                }
            }
        } catch (e: IOException) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        }
    }

    fun writeData(fileName: String, dataList: ArrayList<Array<String>>) {
        try {
            FileWriter(File("$filePath/$fileName")).use { fw ->
                // writeNext()를 이용한 리스트 데이터 등록
                CSVWriter(fw).use {
                    for (data in dataList) {
                        it.writeNext(data)
                    }
                }
            }
        } catch (e: IOException) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        }
    }

    fun readAllCsvData(fileName: String) : List<Array<String>> {
        return try {
            FileReader("$filePath/$fileName").use { fr ->
                // readAll()을 이용해 데이터 읽기
                CSVReader(fr).use {
                    it.readAll()
                }
            }
        } catch (e: IOException) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }

            listOf()
        }
    }

    fun readCsvData(fileName: String) : List<Array<String>> {
        return try {
            FileReader("$filePath/$fileName").use { fr ->
                val dataList = arrayListOf<Array<String>>()

                //for문을 이용해 데이터 읽기
                CSVReader(fr).use {
                    for (data in it) {
                        dataList.add(data)
                    }
                }

                dataList
            }
        } catch (e: IOException) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }

            listOf()
        }
    }

    suspend fun csvToRoom() {
        withContext(Dispatchers.IO) {
            stationRepository.deleteAll()
            FileReader("$csvPath/bus.csv").use { fr ->
                CSVReader(fr).use {
                    it.readAll().forEach { array ->
                        stationRepository.insert(
                            Station(
                                array[0].replace(Regex("[^0-9]+"), "").toInt(),
                                array[1].toString(),
                                array[2].replace(Regex("[^0-9]+"), "").toDouble(),
                                array[3].replace(Regex("[^0-9]+"), "").toDouble(),
                                array[4].replace(Regex("[^0-9]+"), "").toInt(),
                                array[5].replace("]", "")
                            )
                        )
                    }
                }
            }
        }
    }       // csv 파일의 내용을 Station 형태로 Room에 추가


    /*
    //데이터 추가 예시
dataList1.add(arrayOf("Name", "Age"))


dataList1.add(arrayOf("Kim", "28"))
dataList1.add(arrayOf("Lee", "22"))
dataList1.add(arrayOf("Kawk", "25"))
csvHelper.writeAllData(FILE_NAME_1, dataList1)

 */

}