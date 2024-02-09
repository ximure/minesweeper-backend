package com.ximure.minesweeper.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// подставка URL на сайте не исключала домен "https://minesweeper-test.studiotg.ru/" из полного пути
// поэтому решил скачать HTML-файл с фронтом чтобы это обойти
@RestController
public class GameController {
    @GetMapping("/")
    public String getGame() {
        return "<!DOCTYPE html>\n" +
                "<!-- saved from url=(0037)https://minesweeper-test.studiotg.ru/ -->\n" +
                "<html lang=\"ru\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    \n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Minesweeper</title>\n" +
                "    <style>\n" +
                "      body {\n" +
                "        font-family: sans-serif;\n" +
                "      }\n" +
                "      fieldset {\n" +
                "        border: lightgray 1px solid;\n" +
                "      }\n" +
                "      legend {\n" +
                "        color: gray;\n" +
                "      }\n" +
                "      label {\n" +
                "        display: block;\n" +
                "        float: left;\n" +
                "        text-align: right;\n" +
                "        padding-right: 10pt;\n" +
                "        width: 45%;\n" +
                "      }\n" +
                "      .field-size input, .mines-count input {\n" +
                "        width: 40pt;\n" +
                "      }\n" +
                "      small, small a {\n" +
                "        color: gray;\n" +
                "      }\n" +
                "      table {\n" +
                "        border-collapse: collapse;\n" +
                "        margin: 20px auto;\n" +
                "      }\n" +
                "      caption {\n" +
                "        margin-bottom: 5pt;\n" +
                "      }\n" +
                "      td {\n" +
                "        border: 1px solid #ccc;\n" +
                "        text-align: center;\n" +
                "        width: 25px;\n" +
                "        height: 25px;\n" +
                "        cursor: not-allowed;\n" +
                "      }\n" +
                "      td.cell-unknown {\n" +
                "        cursor: pointer;\n" +
                "        background-color: #f6f6f6;\n" +
                "      }\n" +
                "      td.cell-unknown:hover {\n" +
                "        background-color: #eee;\n" +
                "      }\n" +
                "      td.cell-0 {\n" +
                "        color: #eeeeee;\n" +
                "      }\n" +
                "      td.cell-1 {\n" +
                "        color: blue;\n" +
                "      }\n" +
                "      td.cell-2 {\n" +
                "        color: gray;\n" +
                "      }\n" +
                "      td.cell-3 {\n" +
                "        color: orange;\n" +
                "      }\n" +
                "      td.cell-4 {\n" +
                "        color: orangered;\n" +
                "      }\n" +
                "      td.cell-5 {\n" +
                "        color: red;\n" +
                "      }\n" +
                "      td.cell-6 {\n" +
                "        color: darkred;\n" +
                "      }\n" +
                "      td.cell-7 {\n" +
                "        color: firebrick;\n" +
                "      }\n" +
                "      td.cell-7 {\n" +
                "        color: brown;\n" +
                "      }\n" +
                "      td.cell-X {\n" +
                "        color: white;\n" +
                "        background-color: red;\n" +
                "      }\n" +
                "      td.cell-M {\n" +
                "        color: white;\n" +
                "        background-color: green;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body class=\"vsc-initialized\">\n" +
                "\n" +
                "    <h1>Сапёр</h1>\n" +
                "\n" +
                "    <fieldset>\n" +
                "      <legend>Настройки игры</legend>\n" +
                "\n" +
                "      <form id=\"new_game\" onsubmit=\"postNew(); return false;\">\n" +
                "\n" +
                "        <p>\n" +
                "          <label for=\"url\">URL API (можно относительный путь):</label>\n" +
                "          <input id=\"url\" value=\"/api\">\n" +
                "          <small>(<a href=\"https://minesweeper-test.studiotg.ru/swagger/\">спецификация</a>)</small>\n" +
                "        </p>\n" +
                "\n" +
                "        <p class=\"field-size\">\n" +
                "          <label>Размер игрового поля (ширина x высота):</label>\n" +
                "          <input name=\"width\" type=\"number\" min=\"2\" step=\"1\" value=\"10\" required=\"\">\n" +
                "          x\n" +
                "          <input name=\"height\" type=\"number\" min=\"2\" step=\"1\" value=\"10\" required=\"\">\n" +
                "        </p>\n" +
                "\n" +
                "        <p class=\"mines-count\">\n" +
                "          <label>Количество мин:</label>\n" +
                "          <input name=\"mines_count\" type=\"number\" min=\"1\" step=\"1\" value=\"10\" required=\"\">\n" +
                "        </p>\n" +
                "\n" +
                "        <p>\n" +
                "          <label>&nbsp;</label>\n" +
                "          <input type=\"submit\" value=\"Новая игра\" name=\"button\">\n" +
                "        </p>\n" +
                "      </form>\n" +
                "\n" +
                "    </fieldset>\n" +
                "  \n" +
                "\n" +
                "  <script>\n" +
                "    const stateGame = {\n" +
                "      matrix: [],\n" +
                "      isLoading: false,\n" +
                "      game_id: \"\",\n" +
                "    };\n" +
                "\n" +
                "    function startGame({ game_id, field }) {\n" +
                "      removeTable();\n" +
                "      stateGame.game_id = game_id;\n" +
                "      createTable(field);\n" +
                "    }\n" +
                "\n" +
                "    function removeTable() {\n" +
                "      const table = document.getElementById(\"table\");\n" +
                "      if (table) {\n" +
                "        document.body.removeChild(table);\n" +
                "        stateGame.matrix.length = 0;\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "    function createTable(fields) {\n" +
                "      const caption = document.createElement(\"caption\");\n" +
                "      caption.textContent = \"Идет игра\";\n" +
                "\n" +
                "      const table = document.createElement(\"table\");\n" +
                "      table.setAttribute(\"id\", \"table\");\n" +
                "      table.appendChild(caption);\n" +
                "\n" +
                "      fields.forEach((row, i) => {\n" +
                "        const rows = [];\n" +
                "        const tr = document.createElement(\"tr\");\n" +
                "\n" +
                "        row.forEach((value, j) => {\n" +
                "          const td = document.createElement(\"td\");\n" +
                "          td.textContent = value;\n" +
                "          td.setAttribute(\"row\", i);\n" +
                "          td.setAttribute(\"col\", j);\n" +
                "          td.className = \"cell-unknown\";\n" +
                "          td.addEventListener(\"click\", postTurn);\n" +
                "          tr.appendChild(td);\n" +
                "          rows.push(td);\n" +
                "        });\n" +
                "\n" +
                "        table.appendChild(tr);\n" +
                "        stateGame.matrix.push(rows);\n" +
                "      });\n" +
                "\n" +
                "      const body = document.body;\n" +
                "      body.appendChild(table);\n" +
                "    }\n" +
                "\n" +
                "    function updateTable(fields) {\n" +
                "      let isVictory = true;\n" +
                "      let isDefeat = false;\n" +
                "\n" +
                "      forEachMatrix(stateGame.matrix, (td, i, j) => {\n" +
                "        const value = fields[i][j];\n" +
                "        td.textContent = fields[i][j];\n" +
                "        td.className = \"cell-\" + (value == \" \" ? \"unknown\" : value);\n" +
                "        if (value === \" \") {\n" +
                "          isVictory = false;\n" +
                "        } else if (value === \"X\") {\n" +
                "          isDefeat = true;\n" +
                "        }\n" +
                "      });\n" +
                "\n" +
                "      if (isDefeat) {\n" +
                "        updateStatus(\"Вы проиграли ☹\");\n" +
                "      } else if (isVictory) {\n" +
                "        updateStatus(\"Вы победили ☺\");\n" +
                "      } else {\n" +
                "        updateStatus(\"Играем!\");\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "    function updateStatus(text) {\n" +
                "      const table = document.getElementById(\"table\");\n" +
                "      const caption = table.getElementsByTagName(\"caption\")[0];\n" +
                "      caption.textContent = text;\n" +
                "    }\n" +
                "\n" +
                "    function forEachMatrix(array, cb) {\n" +
                "      array.forEach((tr, i) => {\n" +
                "        tr.forEach((td, j) => {\n" +
                "          cb(td, i, j);\n" +
                "        });\n" +
                "      });\n" +
                "    }\n" +
                "\n" +
                "    async function postNew() {\n" +
                "      const { elements } = document.getElementById(\"new_game\");\n" +
                "      const button = elements.button;\n" +
                "\n" +
                "      const body = {\n" +
                "        width: +elements.width.value,\n" +
                "        height: +elements.height.value,\n" +
                "        mines_count: +elements.mines_count.value,\n" +
                "      };\n" +
                "\n" +
                "      button.value = \"Загрузка...\";\n" +
                "      await post(\"new\", body, startGame);\n" +
                "      button.value = \"Новая игра\";\n" +
                "    }\n" +
                "\n" +
                "    async function postTurn() {\n" +
                "      if (stateGame.isLoading) return;\n" +
                "      const body = {\n" +
                "        game_id: stateGame.game_id,\n" +
                "        row: +this.getAttribute(\"row\"),\n" +
                "        col: +this.getAttribute(\"col\"),\n" +
                "      };\n" +
                "      updateStatus(\"Загрузка...\");\n" +
                "      await post(\n" +
                "        \"turn\",\n" +
                "        body,\n" +
                "        ({ field }) => updateTable(field)\n" +
                "      );\n" +
                "    }\n" +
                "\n" +
                "    async function post(path, body, resolve, reject = () => null) {\n" +
                "      const url = document.getElementById(\"url\").value;\n" +
                "      await fetch(`${url}/${path}`, {\n" +
                "        method: \"POST\",\n" +
                "        headers: {\n" +
                "          \"Content-Type\": \"application/json;charset=utf-8\",\n" +
                "        },\n" +
                "        body: JSON.stringify(body),\n" +
                "      })\n" +
                "        .then(async (response) => {\n" +
                "          const data = await response.json();\n" +
                "          if (response.ok) {\n" +
                "            resolve(data);\n" +
                "          } else {\n" +
                "            throw new Error(data.error || \"Технические шоколадки\");\n" +
                "          }\n" +
                "        })\n" +
                "        .catch(async (e) => {\n" +
                "          reject();\n" +
                "          alert(e.message);\n" +
                "        });\n" +
                "    }\n" +
                "  </script>\n" +
                "\n" +
                "</body></html>";
    }
}
