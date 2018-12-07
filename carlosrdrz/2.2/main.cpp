#include <iostream>
#include <fstream>
#include <algorithm>
#include <vector>

int countDiffChars(std::string one, std::string two) {
  auto length = one.length();
  int result = length;

  for (int i = 0; i < length; i++) {
    if (one.at(i) == two.at(i)) result--;
  }

  return result;
}

std::string removeDiffChars(std::string one, std::string two) {
  auto length = one.length();
  std::string result;

  for (int i = 0; i < length; i++) {
    if (one.at(i) == two.at(i)) result = result + one.at(i);
  }

  return result;
}

int main(int argc, char* argv[]) {
  std::ifstream fs(argv[1], std::ifstream::in);
  std::vector<std::string> ids;
  std::string line;

  while(std::getline(fs, line)) {
    ids.push_back(line);
  }

  fs.close();

  for (int i = 0; i < ids.size(); i++) {
    for (int j = i + 1; j < ids.size(); j++) {
      auto diff = countDiffChars(ids[i], ids[j]);

      if (diff == 1) {
        std::cout << "Found! " << removeDiffChars(ids[i], ids[j]) << std::endl;
      }
    }
  }

  fs.close();
  return EXIT_SUCCESS;
}
