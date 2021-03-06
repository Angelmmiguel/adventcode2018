#include <iostream>
#include <fstream>
#include <regex>
#include <algorithm>

typedef struct {
  int id;
  int x;
  int y;
  int width;
  int height;
} piece;

piece parsePiece(std::string p) {
  std::smatch match;
  std::regex rgx("^#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");

  std::regex_search(p, match, rgx);

  piece parsedPiece;
  parsedPiece.id = std::stoi(match[1]);
  parsedPiece.x = std::stoi(match[2]);
  parsedPiece.y = std::stoi(match[3]);
  parsedPiece.width = std::stoi(match[4]);
  parsedPiece.height = std::stoi(match[5]);

  return parsedPiece;
}

int main(int argc, char* argv[]) {
  std::ifstream fs(argv[1], std::ifstream::in);
  std::vector<piece> pieces;

  int max_width = 0;
  int max_height = 0;

  std::string line;
  while(std::getline(fs, line)) {
    auto p = parsePiece(line);
    max_width = std::max(p.x + p.width, max_width);
    max_height = std::max(p.y + p.height, max_height);
    pieces.push_back(p);
  }

  fs.close();

  auto grid = new int*[max_height];
  for (int i = 0; i < max_height; i++) {
    grid[i] = new int[max_width];

    for (int j = 0; j < max_width; j++) {
      grid[i][j] = 0;
    }
  }

  int num_invalid_claims = 0;
  for (auto p : pieces) {
    for (int i = p.y; i < p.y + p.height; i++) {
      for (int j = p.x; j < p.x + p.width; j++) {
        if (grid[i][j] >= 0) grid[i][j]++;
        if (grid[i][j] >= 2) {
          num_invalid_claims++;
          grid[i][j] = -1;
        }
      }
    }
  }

  std::cout << "Num invalid claims " << num_invalid_claims << std::endl;

  for (int i = 0; i < max_height; i++) {
    delete grid[i];
  }

  delete[] grid;

  return EXIT_SUCCESS;
}
